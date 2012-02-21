package jp.co.dgic.target;

import java.util.ResourceBundle;

public class ResourceBundleUser {
	
	public static void main(String[] args) {
		ResourceBundleUser rb = new ResourceBundleUser();
		rb.newTes0();
	}
	
	public void newTes0() {
		new Tes0();
	}

	public void getBundle() {
		// fileName‚É'$'‚ª“ü‚Á‚Ä‚¢‚é
		ResourceBundle.getBundle("resourcebundleuser$1");
	}

    public class Tes0 {
        Tes0() {
            //ResourceTest$Tes0.properties‚ð—pˆÓ‚Ì‚±‚Æ
            ResourceBundle _resBundle = ResourceBundle.getBundle(getClass().getName());
        }
    }
}
