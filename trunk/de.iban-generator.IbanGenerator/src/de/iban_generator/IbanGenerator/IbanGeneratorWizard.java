package de.iban_generator.IbanGenerator;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class IbanGeneratorWizard {

	private String blz = "";
	private String ktnr = "";
	private Text resultText;
	private Composite root;

	/**
	 * Creates the UI content. It consists of a grid with a label on the left side and the corresponding input field on
	 * the right side. Finally, the calculate button is located on the left and the result on the right side.
	 * 
	 * @param parent
	 */
	@Inject
	public void createControl(final Composite parent) {
		this.root = parent;
		GridLayout grid = new GridLayout(2, true);
		this.root.setBackground(new Color(this.root.getBackground().getDevice(), new RGB(220, 231, 245)));
		this.root.setLayout(grid);
		createBLZcontrol();
		createKtnrControl();
		createIbanControl();
		createGenerateButton();
	}

	@Focus
	public void setFocus() {
		this.root.setFocus();
	}

	/**
	 * Creates the UI for the BLZ, with a label on the left side and the corresponding input field on the right side.
	 */
	private void createBLZcontrol() {
		Label l = new Label(this.root, SWT.NONE);
		l.setText("BLZ eingeben:");
		l.setFont(getFont());
		final Text text = new Text(this.root, SWT.NONE);
		text.setFont(getFont());
		GridData data = new GridData(100, SWT.DEFAULT);
		text.setLayoutData(data);
		text.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IbanGeneratorWizard.this.blz = text.getText();
			}
		});
	}

	/**
	 * Creates the UI for the button to generate the IBAN using the {@link IbanGenerator}.
	 */
	private void createGenerateButton() {
		final Button btn = new Button(this.root, SWT.PUSH);
		btn.setText("Berechne IBAN");
		btn.addSelectionListener(new SelectionListener() {

			/**
			 * Calculates the IBAN using the {@link IbanGenerator} and sets the IBAN as value of the text field
			 * containing the result.
			 */
			private void calculateIban() {
				String iban = IbanGenerator.calculateIban(IbanGeneratorWizard.this.root, IbanGeneratorWizard.this.blz,
						IbanGeneratorWizard.this.ktnr);
				IbanGeneratorWizard.this.resultText.setText(iban);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				calculateIban();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				calculateIban();
			}
		});
	}

	/**
	 * Creates the UI for the IBAN, with a label on the left side and the corresponding input field showing the
	 * calculated IBAN on the right side.
	 */
	private void createIbanControl() {
		Label l = new Label(this.root, SWT.NONE);
		l.setText("Generierte IBAN:");
		l.setFont(getFont());
		this.resultText = new Text(this.root, SWT.NONE);
		this.resultText.setFont(getFont());
		GridData data = new GridData(210, SWT.DEFAULT);
		this.resultText.setLayoutData(data);
	}

	/**
	 * Creates the UI for the Kontonummer, with a label on the left side and the corresponding input field on the right
	 * side.
	 */
	private void createKtnrControl() {
		Label l = new Label(this.root, SWT.NONE);
		l.setText("Kontonummer eingeben:");
		l.setFont(getFont());
		final Text text = new Text(this.root, SWT.NONE);
		text.setFont(getFont());
		GridData data = new GridData(100, SWT.DEFAULT);
		text.setLayoutData(data);
		text.addListener(SWT.Modify, new Listener() {

			@Override
			public void handleEvent(Event event) {
				IbanGeneratorWizard.this.ktnr = text.getText();
			}
		});
	}

	/**
	 * @return the used font with 12pt and bold.
	 */
	private Font getFont() {
		FontData fd = new FontData();
		fd.setStyle(SWT.BOLD);
		fd.setHeight(12);
		return new Font(this.root.getBackground().getDevice(), fd);
	}
}
