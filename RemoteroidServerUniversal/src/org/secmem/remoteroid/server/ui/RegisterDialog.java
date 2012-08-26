package org.secmem.remoteroid.server.ui;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.secmem.remoteroid.lib.api.API;
import org.secmem.remoteroid.lib.data.Account;
import org.secmem.remoteroid.lib.request.Request;
import org.secmem.remoteroid.lib.request.Request.RequestFactory;
import org.secmem.remoteroid.lib.request.Response;

public class RegisterDialog extends Dialog {

	protected Object result;
	protected Shell shlRegister;
	private Text txtEmail;
	private Label lblPassword;
	private Text txtPassword;
	private Text txtVerifyPassword;
	private Label lblStatus;
	private Button btnRegister;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public RegisterDialog(Shell parent, int style) {
		super(parent, style);
		setText("Register");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlRegister.open();
		shlRegister.layout();
		Display display = getParent().getDisplay();
		while (!shlRegister.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	
	private void refreshDialogState(){
		if(txtEmail.getText().length()==0){
			lblStatus.setText("Enter E-mail address");
			btnRegister.setEnabled(false);
			return;
		}
		
		if(txtPassword.getText().length()==0){
			lblStatus.setText("Enter password");
			btnRegister.setEnabled(false);
			return;
		}
		
		if(txtVerifyPassword.getText().length()==0){
			lblStatus.setText("Verify password");
			btnRegister.setEnabled(false);
			return;
		}
		
		if(!txtPassword.getText().equals(txtVerifyPassword.getText())){
			lblStatus.setText("Password does not matches");
			btnRegister.setEnabled(false);
			return;
		}
		
		lblStatus.setText("");
		btnRegister.setEnabled(true);
		
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlRegister = new Shell(getParent(), SWT.DIALOG_TRIM);
		shlRegister.setSize(306, 232);
		shlRegister.setText("Register");
		
		txtEmail = new Text(shlRegister, SWT.BORDER);
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				refreshDialogState();
			}
		});
		txtEmail.setBounds(10, 44, 286, 19);
		
		Label lblEmailAddress = new Label(shlRegister, SWT.NONE);
		lblEmailAddress.setBounds(10, 24, 122, 14);
		lblEmailAddress.setText("E-mail address");
		
		lblPassword = new Label(shlRegister, SWT.NONE);
		lblPassword.setBounds(10, 69, 59, 14);
		lblPassword.setText("Password");
		
		txtPassword = new Text(shlRegister, SWT.BORDER | SWT.PASSWORD);
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				refreshDialogState();
			}
		});
		txtPassword.setBounds(10, 89, 286, 19);
		
		btnRegister = new Button(shlRegister, SWT.NONE);
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Account account = new Account();
				account.setEmail(txtEmail.getText());
				account.setPassword(txtPassword.getText());
				
				final Request request = RequestFactory.getRequest(API.Account.ADD_ACCOUNT).attachPayload(account);
				
				new Thread(new Runnable(){
					public void run(){
						try{
							Response response = request.sendRequest();
							if(response.isSucceed()){
								System.out.println("OK");
							}else{
								System.out.println("Failed");
							}
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		btnRegister.setEnabled(false);
		btnRegister.setBounds(202, 167, 94, 28);
		btnRegister.setText("Register");
		
		lblStatus = new Label(shlRegister, SWT.NONE);
		lblStatus.setBounds(10, 174, 59, 14);
		lblStatus.setText("");
		
		Label lblVerifyPassword = new Label(shlRegister, SWT.NONE);
		lblVerifyPassword.setBounds(10, 114, 89, 14);
		lblVerifyPassword.setText("Verify Password");
		
		txtVerifyPassword = new Text(shlRegister, SWT.BORDER | SWT.PASSWORD);
		txtVerifyPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				refreshDialogState();
			}
		});
		txtVerifyPassword.setBounds(10, 134, 286, 19);

	}
}