import javafx.scene.control.TextField;

// se http://fxexperience.com/2012/02/restricting-input-on-a-textfield/ 
public class SingleSymbolField extends TextField { //subklass till TextField

	@Override
	public void replaceText(int start, int end, String text) {
		if (matches(text)) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (matches(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean matches(String text) {
		return text.isEmpty() || (getText().length() < 1 && text.matches("[1-9]"));
	}
}
