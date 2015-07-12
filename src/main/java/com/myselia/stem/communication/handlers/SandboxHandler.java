package com.myselia.stem.communication.handlers;

import java.io.IOException;

import com.myselia.javacommon.communication.mail.MailService;
import com.myselia.javacommon.communication.units.Transmission;
import com.myselia.javacommon.communication.units.TransmissionBuilder;
import com.myselia.javacommon.constants.opcode.ActionType;
import com.myselia.javacommon.constants.opcode.ComponentType;
import com.myselia.javacommon.constants.opcode.OpcodeBroker;
import com.myselia.javacommon.constants.opcode.operations.SandboxMasterOperation;
import com.myselia.javacommon.constants.opcode.operations.StemOperation;

public class SandboxHandler extends ComponentHandlerBase {

	public SandboxHandler() {
		//String toOpcode = OpcodeBroker.makeMailCheckingOpcode(ComponentType.SANDBOXMASTER, ActionType.DATA);
		//String toOpcode2 = OpcodeBroker.makeMailCheckingOpcode(ComponentType.SANDBOXMASTER, ActionType.RUNTIME);
		MailService.register("SANDBOXMASTER_DATA", this);
		MailService.register("SANDBOXMASTER_RUNTIME", this);
	}

	public void handleComponent(Transmission t) throws IOException {
		super.handleComponent(t);
	}
	
	@Override
	protected void transmissionReceived(Transmission t) {
		mailbox.enqueueOut(t);
		MailService.notify(this);
	}

	@Override
	protected void endpointReceive() {
		write(mailbox.dequeueIn());
	}

	public Transmission testTrans(int num) {
		TransmissionBuilder tb = new TransmissionBuilder();
		String from = OpcodeBroker.make(ComponentType.STEM, null, ActionType.DATA, StemOperation.TEST);
		String to = OpcodeBroker.make(ComponentType.SANDBOXMASTER, null, ActionType.DATA, SandboxMasterOperation.RESULTCONTAINER);
		tb.newTransmission(from, to);
		tb.addAtom("someNumber", "int", Integer.toString(num));
		return tb.getTransmission();
	}

}
