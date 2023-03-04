/*=============================================================================
| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
|
| Author: Deodatus Marcellino
| Language: c, c++, Java
|
| To Compile: javac pa01.java
| gcc -o pa01 pa01.c
| g++ -o pa01 pa01.cpp
|
| To Execute: java -> java pa01 kX.txt pX.txt
| or c++ -> ./pa01 kX.txt pX.txt
| or c -> ./pa01 kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2021
| Instructor: McAlpin
| Due Date: 10/24/2021
|
+=============================================================================*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class pa01{
    public static void main(String[] Args) throws Exception {
    
        // Declare Variables & Datatypes
        File encryption_key_file = new File(Args[0]);
        File plaintext_file = new File(Args[1]);

        String key = new String();
        String plaintext = new String();
        String ciphertext = new String();
        
        // Read the encryption key file
        key = reading_files(key, encryption_key_file);
        
        // Read the file to be encrypted (Plaintext)
        plaintext = reading_files(plaintext, plaintext_file);
        
        // Encryption calculation 
        ciphertext = encryption(initialize(plaintext), initialize(key, initialize(plaintext)));
        
        // Output
        printout(key.toLowerCase().replaceAll("[\\W0-9/_]", ""), plaintext, ciphertext);
    }
// ================================ Main Methods ======================== // 
    static void printout(String key, String pt, String ct)
    {    
        System.out.println("\n\nVigenere Key:\n\n" + cookie_cutter(key) + "\n\n");
        System.out.println("Plaintext:\n\n" + cookie_cutter(initialize(pt)) + "\n\n");
        System.out.println("Ciphertext:\n\n" + cookie_cutter(ct));
    }

    static String reading_files(String str, File inFile)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            String line = new String();

            while((line = br.readLine()) != null)
            {
                str = str+line;
            }
            br.close();
        }
        catch ( Exception e ) {e.printStackTrace();}
        
        return str;
    }

/** =================== Initialization =========================
**  change the characters to lowercases, remove all the whitespace & punctuation
**  pad the rest of plaintext with x.
*/
    static String initialize(String str) 
    {
        str = str.toLowerCase().replaceAll("[\\W0-9/_]", "");

        if(str.length() < 512){
            str = padding(str);
        }
        else if(str.length() > 512){
            str = str.substring(0,512);
        }
        return str;
    } // end method initialize

    static String initialize(String key, String str) // Initialize the encryption key (has two parameters)
    {
        key = key.toLowerCase().replaceAll("[\\W0-9/_]", "");
        key = key(str, key);
        return key;
    } // end method initialize

    static String padding(String str)
    {
        if(str.length() < 512)
        {
            for(int i = str.length(); i < 512; i++)
            {
                str += 'x';
            }
        }
        return str;
    }// end method padding
    
        static String cookie_cutter(String cookie)
    {
        cookie = cookie.replaceAll(".{80}(?=.)", "$0\n");
        return cookie;
    } // end method cookie_cutter
    
    static String key(String str, String key)
    {
        int x = str.length(); 
  
        for (int i = 0; ; i++) 
        { 
            if (x == i) 
                i = 0; 
            if (key.length() == str.length()) 
                break; 
            key+=(key.charAt(i)); 
        } 
        return key; 
    } // end method key
    
// ================== Encryption Method ================= //
    static String encryption(String str, String key)
    {
        String ciphertext = new String();

        for(int i = 0, j = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
			ciphertext += (char)(((c - 97) + (key.charAt(j)-97)) % 26 + 97);
			j = ++j % key.length();

        }
        return ciphertext;
    }// end method encryption
}

/*=============================================================================
| I [Deodatus Marcellino] ([de960692]) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/