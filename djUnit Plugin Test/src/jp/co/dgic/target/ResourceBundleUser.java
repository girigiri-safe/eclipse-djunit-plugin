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
		// fileName��'$'�������Ă���
		ResourceBundle.getBundle("resourcebundleuser$1");
	}

    public class Tes0 {
        Tes0() {
            //ResourceTest$Tes0.properties��p�ӂ̂���
            ResourceBundle _resBundle = ResourceBundle.getBundle(getClass().getName());
        }
    }
}
