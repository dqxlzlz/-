//package org.csu.metrics;
//
//import java.io.File;
//import java.util.ArrayList;
//
//
//public class FileUtils {
//
//	public static String[] getAllDirs(String path) {
//		ArrayList<String> dirs = new ArrayList<String>();
//		getAllDirs(path, dirs);
//
//		String[] ar = new String[dirs.size()];
//		ar = dirs.toArray(ar);
//		return ar;
//	}
//
//	private static void getAllDirs(String path, ArrayList<String> dirs) {
//
//		File f = new File(path);
//		if(f.getName().equals(".git")) return;
//
//		for(File inside : f.listFiles()) {
//			if(inside.isDirectory()) {
//				String newDir = inside.getAbsolutePath();
//				dirs.add(newDir);
//				getAllDirs(newDir, dirs);
//			}
//		}
//	}
//
//	public static String[] getAllJavaFiles(String path) {
//		ArrayList<String> files = new ArrayList<String>();
//		getAllJavaFiles(path, files);
//
//		String[] ar = new String[files.size()];
//		ar = files.toArray(ar);
//		return ar;
//	}
//
//	private static void getAllJavaFiles(String path, ArrayList<String> files) {
//
//		File f = new File(path);
//		if(f.getName().equals(".git")) return;
//
//		for(File inside : f.listFiles()) {
//			if(inside.isDirectory()) {
//				String newDir = inside.getAbsolutePath();
//				getAllJavaFiles(newDir, files);
//			} else if(inside.getAbsolutePath().toLowerCase().endsWith(".java")) {
//				files.add(inside.getAbsolutePath());
//			}
//		}
//	}
//
//
//}
package org.csu.metrics;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
	//Get all directories from the directory at the given path.
	public static String[] getAllDirs(String path) {
		try {
			return Files.walk(Paths.get(path))
					.filter(Files::isDirectory)
					.filter(x -> !isGitDir(x.toAbsolutePath().toString()))
					.map(x -> x.toAbsolutePath().toString())
					.toArray(String[]::new);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	//Get all java class files from the directory at the given path.
	public static String[] getAllJavaFiles(String path) {
		return getAllFiles(path, "java");
	}

	//Get all jars from the directory at the given path.
	public static String[] getAllJars(String path) {
		return getAllFiles(path, "jar");
	}

	//Get all files from of the given file ending from the directory at the given path.
	private static String[] getAllFiles(String path, String ending){
		try {
			return Files.walk(Paths.get(path))
					.filter(Files::isRegularFile)
					.filter(x -> !isGitDir(x.toAbsolutePath().toString()))
					.filter(x -> x.toAbsolutePath().toString().toLowerCase().endsWith(ending))
					.map(x -> x.toAbsolutePath().toString())
					.toArray(String[]::new);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	//Is the given directory a git repository directory?
	private static boolean isGitDir(String path){
		return path.contains("/.git/");
	}
}