/*
 * �c�h�b�ʎ蔠�@������񋤗L�r�e�`�V�X�e��
 * @(#)SengleProcess.java 2004/03/26
 *
 * Copyright 2004 by Dainippon Ink and Chemicals, Incorporated
 *
 * First Code
 * 2004/03/26 kataoka
*/

import java.net.ServerSocket;
import java.net.BindException;

public class SingleProcess{
    public static void main(String[] args) {
        if (!isOnlyMe()) {
            System.err.println("���ɋN������Ă��܂�");
            System.exit(-1);
        }
        System.out.println("�P�ƋN������܂���");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    public static boolean isOnlyMe() {
        try {
            ServerSocket sock = new ServerSocket(PORT);
        } catch (BindException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private static int PORT = 3776;
}

