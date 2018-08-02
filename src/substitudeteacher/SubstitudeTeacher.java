/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package substitudeteacher;

import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class SubstitudeTeacher {
    static Scanner sc=new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i=0;i<10;i++) {
            String origin=sc.nextLine();
            origin=origin.substring(1,origin.length()-2);
            String encripted=sc.nextLine();
            encripted=encripted.substring(1,encripted.length()-2);
            String eMessage=sc.nextLine();
            eMessage=eMessage.substring(1,eMessage.length()-1);
            Encriptor encriptor=new Encriptor();
            if (encriptor.resolve(origin,encripted)) {
                String message=encriptor.decript(eMessage);
                System.out.println("*"+message+"*");
            } else {
                System.out.println("not resolved.");
            }
        }
    }
    
}

class Encriptor {
    int ke=0;
    int kd=0;
    public boolean resolve(String origin,String enc) {
        for (ke=2;ke<=66;ke++) {
            for (kd=2;kd<=66;kd++) {
                boolean found=true;
                for (int i=0;i<origin.length();i++) {
                    int m=encode(origin.charAt(i));
                    if (m==-1) return false;
                    int c=encode(enc.charAt(i));
                    if (c==-1) return false;
                    if (! (ke*m%67 ==c && kd*c%67==m)) {
                        found=false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }
        return false;
    }
    
    int encode(char c) {
        if (c>='A' && c<='Z') return (c-'A'+1);
        if (c>='a' && c<='z') return (c-'a'+27);
        if (c>='0' && c<='9') return (c-'0'+53);
        switch (c) {
            case ' ':
                return 63;
            case '.':
                return 64;
            case ',' :
                return 65;
            case '?':
                return 66;
        }
        return -1;
    }
    
    char decode(int a) {
        if (a>=1 && a<=26) return (char)('A'+a-1);
        if (a>=27 && a<=52) return (char)('a'+a-27);
        if (a>=53 && a<=62) return (char)('0'+a-53);
        switch (a) {
            case 63:
                return ' ';
            case 64:
                return '.';
            case 65:
                return ',';
            case 66:
                return '?';
        }
        return '*';
    }

    String decript(String eMessage) {
        String message="";
        for (char c:eMessage.toCharArray()) {
            int m=kd*encode(c)%67;
            message+=decode(m);
        }
        return message;
    }
}
