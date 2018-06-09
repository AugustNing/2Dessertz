package voogadropbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;

/**
 * 
 * This utility allows the user to upload folders to a dropbox, and also
 * download from that dropbox. The purpose of this utility is to allow users to
 * upload their games onto this public place, and allow users using the same
 * VoogaSalad program to download a created game, save it, and play it locally.
 * 
 * @author Calvin Ma
 *
 */
public class VoogaDropbox {

	private final String ACCESS_TOKEN = "VFY6qzXoWGAAAAAAAAAAG6EdeJfxadAHl-RA-1rgXqgulUGvTxrsCyYULBbtLqW9";
	private DbxClientV2 client;
	private String BASELOCATION;

	/**
	 * Constructor for the class
	 * 
	 * @param gameFolderPath
	 *            - in order to maximize flexibility, the gameFolderPath parameter
	 *            allows the user to give the folder that contains all of the made
	 *            games.
	 */
	public VoogaDropbox(String gameFolderPath) {

		BASELOCATION = gameFolderPath;

		DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
		client = new DbxClientV2(config, ACCESS_TOKEN);

	}

	/**
	 * This function sets up a directory locally for the downloaded game from
	 * dropbox to be placed in. The name of the directory matches the name of the
	 * game in dropbox.
	 * 
	 * @param gameName
	 *            - the name of the game from dropbox as determined from the user
	 * @throws ListFolderErrorException
	 * @throws DbxException
	 * @throws IOException
	 * 
	 *             So, I'm not quite sure what these exceptions are for, but I would
	 *             not expect any files to not be found or directory to not be found
	 *             locally because I am generating them as we go. However, the user
	 *             should keep in mind that if a game with the same name as the one
	 *             being downloaded already exists locally, then the local game will
	 *             be rewritten. The user will need to catch such a case and throw
	 *             an error if they do not want that to happen.
	 * 
	 *             The most important error that will need to be catch is if the app
	 *             calls this method with the name of a game that does not exist on
	 *             the dropbox.
	 * 
	 */
	public void downloadGame(String gameName) throws ListFolderErrorException, DbxException, IOException {
		boolean newGameFolder = (new File(BASELOCATION + gameName).mkdir());
		String totalPath = gameName;
		downloadAllFiles(totalPath);

	}

	/**
	 * 
	 * This is a recursive method that downloads all the files from the dropbox
	 * folder. We did this recursively so to maximize folder flexibility. Regardless
	 * of how deep the files go, this function will be able to retrieve all of them
	 * successfully while building the appropriate directories locally to put the
	 * downloaded files.
	 * 
	 * @param path
	 *            - path to a file/folder at each recursive call
	 * @throws ListFolderErrorException
	 * @throws DbxException
	 * @throws IOException
	 * 
	 *             similar to before, I do not expect these errors to happen - see
	 *             explanation above for more details.
	 */
	private void downloadAllFiles(String path) throws ListFolderErrorException, DbxException, IOException {
		ListFolderResult files = client.files().listFolderBuilder("/games/" + path).start();
		for (Metadata file : files.getEntries()) {
			if (file instanceof FileMetadata) {
				downloadFile("/games/" + path + "/" + file.getName(), BASELOCATION + path + "/" + file.getName());
			} else if (file instanceof FolderMetadata) {
				boolean newGameFolder = (new File(BASELOCATION + path + "/" + file.getName()).mkdir());
				downloadAllFiles(path + "/" + file.getName());
			}
		}

	}

	/**
	 * This method actually does the downloading using an OutputStream and the
	 * Dropbox API. This downloads the file at the appropriate path on the dropbox
	 * to the path dictated locally.
	 * 
	 * @param dropBoxFilePath
	 *            - path of file in dropbox
	 * @param localFileAbsolutePath
	 *            - path of where we want the file to be locally
	 * @throws DbxException
	 * @throws IOException
	 * 
	 *             errors - see above
	 */
	private void downloadFile(String dropBoxFilePath, String localFilePath) throws DbxException, IOException {

		OutputStream downloadFile = new FileOutputStream(localFilePath);

		FileMetadata metadata = client.files().downloadBuilder(dropBoxFilePath).download(downloadFile);

	}

	/**
	 * 
	 * This method sets up the process for uploading all of the files of a
	 * particular game folder onto the dropbox.
	 * 
	 * 
	 * @param gameName
	 *            - name of the game you want to upload
	 * @throws DbxException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * 
	 *             The user will need to catch the FileNotFoundException when a game
	 *             with the wanted "gameName" does not exist. The other errors will
	 *             not occur.
	 * 
	 */

	public void uploadGame(String gameName) throws DbxException, FileNotFoundException, IOException {
		File demo308Folder = new File(BASELOCATION + "/" + gameName);
		String totalPath = gameName;
		uploadAllFiles(totalPath, demo308Folder);
	}

	/**
	 * 
	 * Again, similar to downloadAllFiles, this is a recursive method that will go
	 * through all of the files within the directory associated with a particular
	 * game.
	 * 
	 * @param totalPath
	 *            - the path of the current files. we are appending to this whenever
	 *            a new directory is found so that we can "go into" it.
	 * @param folderName
	 *            - name of the file/folder
	 * @throws UploadErrorException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws DbxException
	 * 
	 *             see downloadGame() for error checking details
	 * 
	 */

	private void uploadAllFiles(String totalPath, File folderName)
			throws UploadErrorException, FileNotFoundException, IOException, DbxException {
		File[] folderList = folderName.listFiles();

		for (File f : folderList) {
			if (f.isFile()) {
				uploadFile(totalPath, f);
			} else if (f.isDirectory()) {
				String deepPath = totalPath + "/" + f.getName();
				uploadAllFiles(deepPath, f);
			}
		}

	}

	/**
	 * 
	 * Using the Dropbox API, this method downloads a file into the Dropbox.
	 * 
	 * @param filePath
	 *            - the path of the folder that the file is in
	 * @param fileName
	 *            - the name of the file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UploadErrorException
	 * @throws DbxException
	 * 
	 *             error checks, details in downloadGame()
	 */
	private void uploadFile(String filePath, File fileName)
			throws FileNotFoundException, IOException, UploadErrorException, DbxException {
		try (InputStream in = new FileInputStream(BASELOCATION + "/" + filePath + "/" + fileName.getName())) {
			FileMetadata metadata = client.files().uploadBuilder("/games/" + filePath + "/" + fileName.getName())
					.uploadAndFinish(in);
		}

	}

	/**
	 * 
	 * creates a new Folder within the Dropbox. Need to call this method whenever a
	 * file needs to be uploaded
	 * 
	 * @param folderName
	 *            - name of the folder for the game, should be the same as the
	 *            gameName
	 * @throws DbxException
	 *             - will not run an error
	 */

	public void createFolder(String folderName) throws DbxException {
		FolderMetadata folder = client.files().createFolder(folderName);

	}

	public List<String> getOnlineGames() throws ListFolderErrorException, DbxException {

		List<String> gameNames = new ArrayList<>();
		ListFolderResult files = client.files().listFolderBuilder("/games/").start();
		for (Metadata file : files.getEntries()) {
			gameNames.add(file.getName());
		}

		return gameNames;
	}

}
