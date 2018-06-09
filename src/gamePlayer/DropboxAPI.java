package gamePlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.CreateFolderErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;

import data.propertiesFiles.ResourceBundleManager;

public class DropboxAPI {

	private static final String ACCESS_TOKEN = "VFY6qzXoWGAAAAAAAAAAG6EdeJfxadAHl-RA-1rgXqgulUGvTxrsCyYULBbtLqW9";
	private static DbxClientV2 client;
	private final static String BASELOCATION = ResourceBundleManager.getPath("BASELOCATION");

	public static void downloadFile(String dropBoxFilePath, String localFileAbsolutePath)
			throws DbxException, IOException {
		// output file for download --> storage location on local system to download
		// file
		OutputStream downloadFile = new FileOutputStream(localFileAbsolutePath);

		FileMetadata metadata = client.files().downloadBuilder(dropBoxFilePath).download(downloadFile);

	}

	public static void createFolder(String folderName) throws DbxException {
		try {
			FolderMetadata folder = client.files().createFolder(folderName);
			System.out.println(folder.getName());
		} catch (CreateFolderErrorException err) {
			if (err.errorValue.isPath() && err.errorValue.getPathValue().isConflict()) {
				System.out.println("Something already exists at the path.");
			} else {
				System.out.print("Some other CreateFolderErrorException occurred...");
				System.out.print(err.toString());
			}
		} catch (Exception err) {
			System.out.print("Some other Exception occurred...");
			System.out.print(err.toString());
		}
	}

	public static void main(String[] args) throws DbxApiException, DbxException, FileNotFoundException, IOException {
		// Create Dropbox client
		DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
		client = new DbxClientV2(config, ACCESS_TOKEN);

		// ListFolderResult files = client.files().listFolderBuilder("/games/").start();
		// for (Metadata file : files.getEntries()) {
		// System.out.println(file.getName());
		// }

		// now i want to download testdownload - first, make directory locally for new
		// game

		boolean newGameFolder = (new File(BASELOCATION + "/Demo308").mkdir());
		// need to error check this line tho just in case game exists locally already
		// with same name - this might overwrite the current folder idk
		ListFolderResult files = client.files().listFolderBuilder("/games/Demo308").start();
		for (Metadata file : files.getEntries()) {
			if (file instanceof FileMetadata) {
				downloadFile("/games/Demo308/" + file.getName(), BASELOCATION + "/Demo308/" + file.getName());
			}
		}

		// // createFolder("/games"); game folder created in dropbox
		//
		// // now I want to download every file from the game folder
		//
		// // first, get to the path for each game locally
		//
		// String baseLocation = ResourceBundleManager.getPath("BASELOCATION");
		//
		// System.out.println(baseLocation);
		//
		// File gamesFolder = new File(baseLocation);
		//
		// File[] filesList = gamesFolder.listFiles();
		// for (File f : filesList) {
		// System.out.println(f.getName());
		// }
		//
		// // choosing demo308 as test game
		//
		// File demo308Folder = new File(baseLocation + "/Demo308");
		//
		// // make folder inside game folder
		// createFolder("/games/Demo308");
		//
		// // iterate through demo308 folder and get all of folders of files
		//
		// File[] demo308FileList = demo308Folder.listFiles();
		// for (File f : demo308FileList) {
		// if (f.isFile()) {
		// uploadFile(baseLocation, "Demo308", f);
		// } else if (f.isDirectory()) {
		// File[] subgameFolder = f.listFiles();
		// for (File subFile : subgameFolder) {
		// uploadFile(baseLocation, "Demo308" + "/" + f.getName(), subFile);
		// }
		// }
		// }
		//
		// // downloadFile("/jeff8/hi.txt", "data/what.txt");

	}

	private static void uploadFile(String baseLocation, String filePath, File fileName)
			throws FileNotFoundException, IOException, UploadErrorException, DbxException {
		try (InputStream in = new FileInputStream(baseLocation + "/" + filePath + "/" + fileName.getName())) {
			FileMetadata metadata = client.files().uploadBuilder("/games/" + filePath + "/" + fileName.getName())
					.uploadAndFinish(in);
		}

	}

}
