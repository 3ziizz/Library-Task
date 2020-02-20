import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OnicaTask {

	static List<Map<String, String>> bookList = new LinkedList<Map<String, String>>();
	static Scanner myInput = new Scanner(System.in);
	static String readStringInput = null;
	static final String invaildinput = "Sorry its an invaild input :(";
	static String filePath = "test.sql";
	static int largestId = 1;

	public static void main(String[] args) {
		try {
			loadDate();
			reloadApp();
		} catch (Exception e) {
			System.out.println("Exception name : " + e.getClass().getName() + ", Exception : " + e);
			System.err.println("No file to load it");
			//			e.printStackTrace();
			readStringInput = null;
		}
	}

	private static void reloadApp() {
		loadMainPage();
		waitToEnterInput();

		try {
			while (readStringInput != null) {
				String optionNumber = getOptionNumber();
				if (!optionNumber.isEmpty()) {
					if (0 < Integer.parseInt(optionNumber) && Integer.parseInt(optionNumber) < 6) {

						if (readStringInput.isEmpty()) {
							clearScreen();
							myInput.close();
						} else {
							switchBetweenPages(optionNumber);
						}
					} else {
						loadMainPage();
						waitToEnterInput();
					}
				} else {

					loadMainPage();
					waitToEnterInput();
				}
			}
		} catch (Exception e) {
			System.err.println(invaildinput);
		}

	}

	private static void loadDate() throws FileNotFoundException {
		try {
			String line;

			BufferedReader reader = new BufferedReader(new FileReader(filePath));

			while ((line = reader.readLine()) != null) {
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				String str[] = line.split("\n");
				String[] lineSperator = str[0].trim().split(",");
				for (int j = 0; j < lineSperator.length; j++) {
					String[] parts = lineSperator[j].trim().split(":");
					if (parts.length >= 2) {
						String key = parts[0];
						String value = parts[1];
						map.put(key, value);
					} else {
						System.out.println("ignoring line: " + line);
					}
				}
				//				System.out.println("largestId : " + largestId);
				//				System.out.println("Integer.parseInt(map.get(ID) : " + Integer.parseInt(map.get("ID")));

				if (Integer.parseInt(map.get("ID")) >= largestId) {
					largestId = Integer.parseInt(map.get("ID")) + 1;
				}
				bookList.add(map);

			}
		} catch (Exception e) {
			//			System.out.println("Exception name : " + e.getClass().getName() + ", Exception : " + e);
			System.err.println("No file name test.sql");
			System.err.println("genrate new file called test.sql");
			File newFile = new File("test.sql");
			filePath = newFile.toString();
			//		}myObj
			//			e.printStackTrace();
			//			readStringInput = null;
		}

	}

	private static void switchBetweenPages(String optionNumber) {
		switch (optionNumber) {
			case "1":
				viewBooks();
				break;
			case "2":
				addBook();
				break;
			case "3":
				editBooks();
				break;
			case "4":
				search();
				break;
			case "5":
				save();
				break;
			default:
				System.out.println("default");
				break;
		}
	}

	private static void save() {
		String fmap = "";

		for (Map<String, String> map : bookList) {
			if (fmap.isEmpty()) {
				fmap = fmap + map.toString();
			} else {
				fmap = fmap + "\n" + map.toString();
			}

		}
		String rs = fmap.replace("{", "");
		rs = rs.replace("}", "");
		rs = rs.replace("=", ":");

		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(rs.toString());

			writer.close();
		} catch (IOException e) {
			System.out.println("Somthing want wrong while writing in file");
			readStringInput = null;
		}
		System.out.println("Library saved.");
		readStringInput = null;
	}

	private static void addBook() {
		String viewBooksPage = "==== Add a Book ====\n\nPlease enter the following information:";

		clearScreen();
		System.out.println(viewBooksPage);
		viewBooksPage = null;
		Map<String, String> listBooksToadd = new LinkedHashMap<String, String>();

		//		String id = new SimpleDateFormat("yMms").format(new Date()).toString();
		String id = Integer.toString(largestId);
		//		System.out.println("=----------------------id :" + largestId);
		listBooksToadd.put("ID", id);

		viewBooksPage = "\n	Title : ";
		System.out.print(viewBooksPage);

		waitToEnterInput();
		listBooksToadd.put("Title", readStringInput);

		viewBooksPage = viewBooksPage + readStringInput + "\n" + "	Author: ";
		System.out.print(viewBooksPage);

		waitToEnterInput();
		listBooksToadd.put("Author", readStringInput);
		//			System.out.println("readStringInput" + readStringInput);

		viewBooksPage = viewBooksPage + readStringInput + "\n" + "	Description: ";
		System.out.print(viewBooksPage);

		waitToEnterInput();
		viewBooksPage = viewBooksPage + readStringInput;

		listBooksToadd.put("Description", readStringInput);

		bookList.add(listBooksToadd);

		System.out.println("Book [" + id + "] Saved,\nTo return press <Enter>.");
		waitToEnterInput();
	}

	private static void editBooks() {
		String viewBooksPage = "==== Edit Books ====\n" + listBooks()
				+ "\nEnter the book ID of the book you want to edit; to return press <Enter>.";

		clearScreen();
		System.out.println(viewBooksPage);
		while (!readStringInput.isEmpty()) {
			waitToEnterInput();

			if (!readStringInput.isEmpty()) {

				String optionNumber = getOptionNumber();
				editList(optionNumber);
			} else {

			}
		}

	}

	private static void search() {
		clearScreen();
		System.out.println("==== Search ====");
		System.out.print("\nsearch : ");

		while (!readStringInput.isEmpty()) {
			waitToEnterInput();
			System.out.println("\nsearch : " + readStringInput);

			String listSearch = readStringInput;
			String listBooksDetailed = "";

			List<String> bookId = new LinkedList<String>();
			String viewBooksPage = "";

			for (int i = 0; i < bookList.size(); i++) {

				if (bookList.get(i).get("Title").toUpperCase().trim().contains(listSearch.toUpperCase().trim())) {
					bookId.add(bookList.get(i).get("ID"));

					listBooksDetailed = "\n" + "	ID: " + bookList.get(i).get("ID") + "\n" + "	Title: "
							+ bookList.get(i).get("Title") + "\n" + "	Author: " + bookList.get(i).get("Author") + "\n"
							+ "	Description: " + bookList.get(i).get("Description") + "\n";

					viewBooksPage = viewBooksPage + " [" + bookList.get(i).get("ID") + "] "
							+ bookList.get(i).get("Title") + "\n";
				}
			}
			if (!viewBooksPage.isEmpty()) {
				System.out.println(
						"The following books matched your query. Enter the book ID to see more details, or <Enter> to return.");
				System.out.println(viewBooksPage);
			} else {
				System.out.println("Wrong Title :( , please insert correct one" + "\nTo return press AnyKey.");
			}

			while (!readStringInput.isEmpty()) {
				waitToEnterInput();

				boolean wrongId = true;
				if (!readStringInput.isEmpty()) {
					String optionNumber = getOptionNumber();

					for (String selectedID : bookId) {

						if (optionNumber.equalsIgnoreCase(selectedID)) {
							viewBooksDetailed(optionNumber);
							wrongId = false;
						}
					}
				}

				if (wrongId) {
					if (readStringInput instanceof String) {
						System.out.println("Wrong inserted Title :( ,To return press <Enter>.");

					} else {
						System.out.println("Wrong inserted id :( ,insert it correctly or To return press <Enter>.");
					}
				}
			}
		}

	}

	private static void clearScreen() {
		//		System.out.print("\033[H\033[2J");
	}

	private static void viewBooks() {
		String viewBooksPage = "==== View Books ====\n" + listBooks()
				+ "\nTo view details enter the book ID, to return press <Enter>.\n\nBook ID: ";

		clearScreen();
		System.out.print(viewBooksPage);
		while (!readStringInput.isEmpty()) {
			waitToEnterInput();

			if (!readStringInput.isEmpty()) {

				String optionNumber = getOptionNumber();
				viewBooksDetailed(optionNumber);
			} else {

			}
		}

	}

	private static void viewBooksDetailed(String id) {

		if (listBooksDetailed(id) != null) {
			String viewBooksPage = "\n" + listBooksDetailed(id)
					+ "\nTo view details enter the book ID, to return press <Enter>.\n\n" + "Book ID:";

			clearScreen();
			System.out.print(viewBooksPage);
		} else {
			clearScreen();
			System.out.println("Wrong ID :( , please insert correct one"
					+ "\nTo view details enter the book ID, to return press <Enter>.");
		}

	}

	private static void editList(String id) {
		System.out.println(
				"Book ID: " + id + "\n\n" + "Input the following information. To leave a field unchanged, hit <Enter>");

		int idToEdit = 0;
		for (int j = 0; j < bookList.size(); j++) {

			if (id.equalsIgnoreCase(bookList.get(j).get("ID"))) {
				idToEdit = j;

			}

		}

		if (listBooksToEdit(id) != null) {
			clearScreen();
			String viewBooksPage = null;

			viewBooksPage = "\n	Title [" + listBooksToEdit(id).get("Title") + "]: ";
			System.out.print(viewBooksPage);

			waitToEnterInput();
			if (!readStringInput.isEmpty()) {
				listBooksToEdit(id).put("Title", readStringInput);

				bookList.set(idToEdit, listBooksToEdit(id));
			}
			viewBooksPage = viewBooksPage + readStringInput + "\n" + "	Author: [" + listBooksToEdit(id).get("Author")
					+ "]: ";
			System.out.print(viewBooksPage);

			waitToEnterInput();
			if (!readStringInput.isEmpty()) {
				listBooksToEdit(id).put("Author", readStringInput);
				bookList.set(idToEdit, listBooksToEdit(id));
			}

			viewBooksPage = viewBooksPage + readStringInput + "\n" + "	Description: ["
					+ listBooksToEdit(id).get("Description") + "]: ";
			System.out.print(viewBooksPage);

			waitToEnterInput();
			viewBooksPage = viewBooksPage + readStringInput;

			if (!readStringInput.isEmpty()) {
				listBooksToEdit(id).put("Description", readStringInput);
				bookList.set(idToEdit, listBooksToEdit(id));
			}

			System.out.println(
					"Book saved.\n" + "Enter the book ID of the book you want to edit; to return press <Enter>.");
		} else {
			clearScreen();
			System.out.println("Wrong ID :( , please insert correct one"
					+ "\nTo view details enter the book ID, to return press <Enter>.");
		}

	}

	private static String listBooksDetailed(String id) {
		String listBooksDetailed = null;

		for (int i = 0; i < bookList.size(); i++) {
			if (id.equalsIgnoreCase(bookList.get(i).get("ID"))) {
				listBooksDetailed = "	ID: " + bookList.get(i).get("ID") + "\n" + "	Title: "
						+ bookList.get(i).get("Title") + "\n" + "	Author: " + bookList.get(i).get("Author") + "\n"
						+ "	Description: " + bookList.get(i).get("Description") + "\n";
			}
		}

		return listBooksDetailed;

	}

	private static Map<String, String> listBooksToEdit(String id) {
		Map<String, String> listBooksDetailed = null;

		for (int i = 0; i < bookList.size(); i++) {
			if (id.equalsIgnoreCase(bookList.get(i).get("ID"))) {
				listBooksDetailed = bookList.get(i);

			}
		}

		return listBooksDetailed;

	}

	private static String listBooks() {
		String listBooks = "";
		for (int i = 0; i < bookList.size(); i++) {
			listBooks = listBooks + "[" + bookList.get(i).get("ID") + "]" + bookList.get(i).get("Title") + "\n";
		}

		return listBooks;
	}

	public static void loadMainPage() {
		String mainPage = "==== Book Manager ====\n" + "	1) View all books\n" + "	2) Add a book\n"
				+ "	3) Edit a book\n" + "	4) Search for a book\n" + "	5) Save and exit\n" + "Choose [1-5]:";
		clearScreen();
		System.out.print(mainPage);
	}

	public static String getOptionNumber() {
		String optionNumber = null;
		try {
			optionNumber = readStringInput;

		} catch (Exception e) {
			System.out.println(invaildinput);
			//			myInput.close();
		}
		return optionNumber;

	}
	public static void waitToEnterInput() {
		readStringInput = myInput.nextLine();
	}
}
