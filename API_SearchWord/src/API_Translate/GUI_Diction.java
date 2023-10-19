package API_Translate;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI_Diction extends JFrame {

          private HashMap<String, String> dictionary;
          private JLabel lbResult;
          private JTextField tfResult;
          private JButton btnSearch;
          private JTextField tfEnter;
          private JButton btnUpdate;
          private JButton btnShowAllDic;
          private JButton btnShowStart;
          private JButton btnShowProcesses;
          private ProcessTableModel processTableModel;
          private JTable table;
          public String url = "D:\\Subjects\\Nam_3\\NguyenLy_HDH\\API_SearchWord\\Directoty.txt";

          public GUI_Diction() {
                    init();
                    buttonSearch();
                    buttonUpdate();
                    buttonShowType();
                    buttonShowProcesses();
                    showWordStartWith();
          }

          private void init() {
                    setSize(800, 800);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    setLocationRelativeTo(null);
                    setTitle("Dictionary");
                    setLayout(null);
                    setResizable(false);
                    Font font = new Font("Roboto", Font.ITALIC, 18);

                    JLabel lbEnter = new JLabel("Enter word ");
                    lbEnter.setBounds(10, 50, 100, 30);
                    tfEnter = new JTextField();
                    tfEnter.setBounds(100, 50, 150, 30);
                    tfEnter.setFont(font);

                    lbResult = new JLabel("Result word is searched");
                    lbResult.setBounds(300, 50, 150, 30);
                    tfResult = new JTextField();
                    tfResult.setBounds(450, 50, 150, 30);
                    tfResult.setEditable(false);
                    tfResult.setFont(font);

                    btnSearch = new JButton("Search");
                    btnSearch.setBounds(50, 200, 160, 30);
                    btnSearch.setBackground(Color.red);
                    btnSearch.setForeground(Color.WHITE);

                    btnUpdate = new JButton("Update dictionary");
                    btnUpdate.setBounds(50, 250, 160, 30);
                    btnUpdate.setBackground(Color.red);
                    btnUpdate.setForeground(Color.WHITE);

                    btnShowAllDic = new JButton("Show All word in Dictionary");
                    btnShowAllDic.setBounds(50, 300, 160, 30);
                    btnShowAllDic.setBackground(Color.red);
                    btnShowAllDic.setForeground(Color.WHITE);

                    btnShowStart = new JButton("Show words start with");
                    btnShowStart.setBounds(50, 350, 160, 30);
                    btnShowStart.setBackground(Color.red);
                    btnShowStart.setForeground(Color.WHITE);

                    btnShowProcesses = new JButton("Show Processes");
                    btnShowProcesses.setBounds(50, 400, 160, 30);
                    btnShowProcesses.setBackground(Color.red);
                    btnShowProcesses.setForeground(Color.WHITE);

                    String columns[] = {"Từ", "Nghĩa"};
                    String data[][] = {};
                    DefaultTableModel tbModel = new DefaultTableModel(data, columns);
                    table = new JTable();
                    JScrollPane jsp = new JScrollPane(table);
                    jsp.setBounds(300, 150, 450, 500);

                    this.add(lbEnter);
                    this.add(tfEnter);
                    this.add(lbResult);
                    this.add(tfResult);
                    this.add(btnSearch);
                    this.add(btnUpdate);
                    this.add(btnShowAllDic);
                    this.add(btnShowStart);
                    this.add(btnShowProcesses);
//                    this.add(table);
                    this.add(jsp);

          }

          public void buttonSearch() {
                    dictionary = new HashMap<>();
                    btnSearch.addActionListener(e -> {
                              String input = tfEnter.getText().toLowerCase().trim();
                              String result = "";
                              File file = new File(url);

                              try {
                                        Scanner sc = new Scanner(file);
                                        while (sc.hasNextLine()) {
                                                  String line = sc.nextLine();
                                                  String[] parts = line.split("=");
                                                  if (parts.length == 2) {
                                                            String key = parts[0].trim();
                                                            String value = parts[1].trim();
                                                            dictionary.put(key, value);
                                                  }
                                        }
                              } catch (FileNotFoundException ex) {
                                        JOptionPane.showMessageDialog(this, "read dictionary not success" + ex.getMessage());
                              }

                              if (!input.isEmpty()) {
                                        try {
                                                  if (dictionary.containsKey(input)) {
                                                            result = dictionary.get(input);
                                                  } else {
                                                            throw new Exception("The word you just entered could not be found");
                                                  }
                                        } catch (Exception ex) {
                                                  JOptionPane.showMessageDialog(this, "Oh!: " + ex.getMessage());
                                        }
                              }

                              tfResult.setText(result);
                    });
          }

          public void buttonUpdate() {
                    btnUpdate.addActionListener(e -> {
                              updateDictionary();
                    });
          }

          private void updateDictionary() {
                    String input = tfEnter.getText().toLowerCase().trim();
                    String result = "";

                    if (!input.isEmpty()) {
                              String value = JOptionPane.showInputDialog(this, "Enter the definition for the word \"" + input + "\":");
                              if (value != null && !value.isEmpty()) {
                                        dictionary.put(input, value);

                                        // Ghi từ vào file
                                        try {
                                                  String filePath = url; // thay đổi dường dẫn theo máy mn nhé
                                                  FileWriter writer = new FileWriter(filePath, true);
                                                  writer.write(input + "=" + value + "\n");
                                                  writer.close();
                                                  JOptionPane.showMessageDialog(this, "The word has been added to the dictionary and saved to the file.");
                                        } catch (IOException ex) {
                                                  JOptionPane.showMessageDialog(this, "An error occurred while writing to the file: " + ex.getMessage());
                                        }
                              } else {
                                        JOptionPane.showMessageDialog(this, "Invalid definition. The word was not added to the dictionary.");
                              }
                    } else {
                              JOptionPane.showMessageDialog(this, "Please enter a word to update the dictionary.");
                    }
          }

          public void buttonShowType() { // thêm sự kiện buttonShowType từ đây đến dòng cmt tiếp theo
                    btnShowAllDic.addActionListener(e -> {
                              showTypesOfWord();
                    });
          }

          private void showTypesOfWord() {
                    try {
                              File file = new File(url);
                              StringBuilder sb = new StringBuilder();
                              BufferedReader reader = new BufferedReader(new FileReader(file));
                              String line;
                              while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                        sb.append("\n");
                              }
                              reader.close();

                              JOptionPane.showMessageDialog(this, sb.toString(), "Types of Word", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e) {
                              JOptionPane.showMessageDialog(this, "Failed to read the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
          }

          public void showWordStartWith() {
                    btnShowStart.addActionListener(e -> {
                              String prefix = tfEnter.getText();
                              populateTableWithWordsStartingWith(prefix);
                    });
          }

          private void populateTableWithWordsStartingWith(String prefix) {
                    String columns[] = {"Từ", "Nghĩa"};
                    String data[][] = {};
                    DefaultTableModel tbModel = new DefaultTableModel(data, columns);

                    try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
                              String line;
                              while ((line = reader.readLine()) != null) {
                                        String[] parts = line.split("=");
                                        if (parts.length >= 2) {
                                                  String word = parts[0];
                                                  String meaning = parts[1];

                                                  if (word.startsWith(prefix)) {
                                                            tbModel.addRow(new Object[]{word, meaning});
                                                  }
                                        }
                              }
                    } catch (IOException ex) {
                              ex.printStackTrace();
                    }

                    table.setModel(tbModel);
          }

          public void buttonShowProcesses() {
                    btnShowProcesses.addActionListener(e -> {
                              Object[][] processData = new Object[0][0]; // Mảng rỗng ban đầu
                              Object[] columnNames = {"Process Name"}; // Tên cột
                              processTableModel = new ProcessTableModel(processData, columnNames);
                              table.setModel(processTableModel);
                              showRunningProcesses();
                    });
          }

          class ProcessTableModel extends DefaultTableModel {

                    public ProcessTableModel(Object[][] data, Object[] columnNames) {
                              super(data, columnNames);
                    }

                    @Override
                    public boolean isCellEditable(int row, int column) {
                              return false; // Đặt tất cả các ô không được chỉnh sửa
                    }
          }

          private void showRunningProcesses() {
//       private void showRunningProcesses() {
                    String[] processes = getProcessList();
                    Object[][] processData = new Object[processes.length][1];

                    for (int i = 0; i < processes.length; i++) {
                              processData[i][0] = processes[i];
                    }

                    processTableModel.setDataVector(processData, new Object[]{"Process Name"});
          }

          public static String[] getProcessList() {
                    String os = System.getProperty("os.name").toLowerCase();
                    String[] command;
                    /*Kiểm tra hệ điều hành đang chạy bằng cách sử dụng System.getProperty("os.name"). 
        Nếu hệ điều hành là Windows, chúng ta sử dụng lệnh tasklist để lấy danh sách tiến trình. 
        Ngược lại, nếu hệ điều hành không phải là Windows, chúng ta sử dụng lệnh ps -e.*/
                    if (os.contains("win")) {
                              command = new String[]{"tasklist"};
                    } else {
                              command = new String[]{"ps", "-e"};
                    }
                    try {
                              //Sử dụng Runtime.getRuntime().exec(command) để thực thi lệnh hệ thống và lấy đầu ra của nó.
                              Process process = Runtime.getRuntime().exec(command);
                              //Sử dụng BufferedReader để đọc từng dòng đầu ra từ quá trình thực thi và lưu tất cả các dòng vào StringBuilder.
                              BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                              String line;
                              StringBuilder output = new StringBuilder();
                              while ((line = reader.readLine()) != null) {
                                        //Chia dòng đầu ra thành một mảng các chuỗi bằng cách sử dụng phương thức split("\n") và trả về mảng này.
                                        output.append(line).append("\n");
                              }
                              reader.close();
                              return output.toString().split("\n");
                    } catch (IOException e) {
                              e.printStackTrace();
                    }
                    return new String[0];
          }

          public static boolean getDriveInfo(String drive) {
                    String os = System.getProperty("os.name").toLowerCase();
                    String[] command; //khai báo một mảng để lưu trữ lệnh hệ thống sẽ được thực thi.
                    if (os.contains("win")) {
                              command = new String[]{"cmd.exe", "/c", "dir " + drive};
                    } else {
                              command = new String[]{"df", "-h"};
                    }

                    try {
                              Process process = Runtime.getRuntime().exec(command);
                              BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                              String line;
                              StringBuilder output = new StringBuilder();
                              while ((line = reader.readLine()) != null) {
                                        output.append(line).append("\n");
                              }
                              reader.close();
                              String outputString = output.toString().toLowerCase();
                              return outputString.contains(drive.toLowerCase());
                    } catch (IOException e) {
                              e.printStackTrace();
                    }
                    return false;
          }

          public static void main(String[] args) {
                    GUI_Diction gui = new GUI_Diction();
                    gui.setVisible(true);
          }
}
