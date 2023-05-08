import java.io.*;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class main {
    static BufferedWriter inputBuffer;
    static BufferedReader bufferReader;
    private static int start = 0;
    private static int selection;
    private static SistemaControllo sw;
    private static MessageDigest message;
    private static String sha256_original = "14b382abb8736639dce380ac2c808db5fe72b3250eb198199e8ec4ca42cfc96d";

    public static void main(String[] args){
        String[] let = new String[10];
        try{
            bufferReader = new BufferedReader(new InputStreamReader(System.in));
            sw = new SistemaControllo();
            while(start == 0){
                menu();
                selection = Integer.parseInt(bufferReader.readLine());
                switch(selection){
                    case 1:
                            System.out.print("\nAccount name: ");
                            let[3] = bufferReader.readLine();
                            System.out.print("\nPassword: ");
                            let[4] = bufferReader.readLine();
                            System.out.print("\nRetype password: ");
                            let[5] = bufferReader.readLine();
                            if(let[4].equals(let[5])){
                                sw.insertNode(let[3],let[4],let[5]);
                                System.out.println("Account signed successfuly ..");
                            }else{
                                System.out.println("Password are not equals .. Aborting operation");
                            }
                            break;
                    case 2:
                            System.out.println("Showing archive: ");
                            System.out.println(sw.toString());
                            break;
                    case 3:
                            System.out.print(sw.toString()+"\n\nSelect the cell: ");
                            selection = Integer.parseInt(bufferReader.readLine());
                            if(!sw.isFree(selection)){
                                sw.deleteNode(selection);
                            }
                            break;

                    case 4:
                            System.out.print(sw.toString()+"\n\nSelect the cell: ");
                            selection = Integer.parseInt(bufferReader.readLine());
                            System.out.print("\nYout want to get the password for "+ sw.getCell(selection)+" ?  (Y/N): ");
                            let[3] = bufferReader.readLine();
                            if(let[3].equals("Y")){
                                System.out.print("Insert the password to unlock your page: ");
                                let[3] = bufferReader.readLine();
                                message = MessageDigest.getInstance("SHA-256");
                                byte[] sha256 = message.digest(let[3].getBytes(StandardCharsets.UTF_8)); 
                                if(sha256.equals(sha256_original.getBytes(StandardCharsets.UTF_8))){
                                    System.out.println("Decrypted password: "+sw.decryptNode(selection));
                                }
                                message = null;
                                sha256 = null;
                                let[3] = null;
                            }else{
                                System.out.println("Aborting .. ");
                            }
                            break;
                    case 5:
                            System.out.println("Saving the archive on the same origin path ");
                            sw.serializeInfo(let[0], let[1],let[2]);
                            System.out.println(sw.toString()+"\n\n Archive successfuly saved! ..");
                            break;
                    case 6:
                            System.out.print("Path to load the File: ");
                            let[0] = bufferReader.readLine();
                            System.out.print("Path to load the Key: ");
                            let[1] = bufferReader.readLine();
                            System.out.print("Path to load the partial method: ");
                            let[2] = bufferReader.readLine();
                            sw.loadData(let[0], let[1],let[2]);
                            break;
                    case 0:
                            start = 1;
                            break;
                    default:
                            break;
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    public static void menu(){
        System.out.println("Encrypt/Decrypt password archive\n");
        System.out.println("1 - new page");
        System.out.println("2 - show current archive");
        System.out.println("3 - delete page");
        System.out.println("4 - show page option");
        System.out.println("5 - Save");
        System.out.println("6 - Load");
        System.out.println("\n0 - Quit");
        System.out.print("\n\nselection: ");
    }
}
