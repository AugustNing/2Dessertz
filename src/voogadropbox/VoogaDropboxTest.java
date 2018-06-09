package voogadropbox;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderErrorException;

import data.propertiesFiles.ResourceBundleManager;

public class VoogaDropboxTest {
	// BASELOCATION is the location of our game files
	private final static String BASELOCATION = ResourceBundleManager.getPath("BASELOCATION");

	public static void main(String[] args) {
		System.out.println(BASELOCATION);
		VoogaDropbox vd = new VoogaDropbox(BASELOCATION);
		try {
			// uploads a game to the dropbox and then downloads the same game. very simple
			// use as long as your
			// path to the game folder is correct
			vd.uploadGame("enginetestmario");
			vd.downloadGame("enginetestmario");
		} catch (Exception e) {
			// error checking will be done here depending on if you don't want folder to be
			// overwritten, or if the name of the folder doesn't exist (game doesn't exist
			// locally or in dropbox
		}
	}

}
