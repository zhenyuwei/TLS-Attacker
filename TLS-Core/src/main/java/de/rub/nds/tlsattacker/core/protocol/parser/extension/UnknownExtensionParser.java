/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.protocol.message.extension.UnknownExtensionMessage;

public class UnknownExtensionParser extends ExtensionParser<UnknownExtensionMessage> {

    public UnknownExtensionParser(int startposition, byte[] array) {
        super(startposition, array);
    }

    protected void parseExtensionData(UnknownExtensionMessage message) {
        if (getBytesLeft() == 0) {
            // No bytes left for extension data
        } else if (getBytesLeft() < message.getExtensionLength().getValue()) {
            message.setExtensionData(parseByteArrayField(getBytesLeft()));
            LOGGER.debug("ExtensionData: " + ArrayConverter.bytesToHexString(message.getExtensionData().getValue()));
        } else {
            message.setExtensionData(parseByteArrayField(message.getExtensionLength().getValue()));
            LOGGER.debug("ExtensionData: " + ArrayConverter.bytesToHexString(message.getExtensionData().getValue()));
        }
    }

    @Override
    public void parseExtensionMessageContent(UnknownExtensionMessage message) {
        if (hasExtensionData(message)) {
            parseExtensionData(message);
        }
    }

    @Override
    protected UnknownExtensionMessage createExtensionMessage() {
        return new UnknownExtensionMessage();
    }
}
