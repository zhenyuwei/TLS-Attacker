/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.tls.protocol.parser;

import de.rub.nds.tlsattacker.tls.constants.HandshakeByteLength;
import de.rub.nds.tlsattacker.tls.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.tls.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.tls.protocol.message.HelloRetryRequestMessage;
import de.rub.nds.tlsattacker.tls.protocol.message.extension.ExtensionMessage;
import de.rub.nds.tlsattacker.tls.protocol.parser.extension.ExtensionParser;
import de.rub.nds.tlsattacker.tls.protocol.parser.extension.ExtensionParserFactory;
import de.rub.nds.tlsattacker.util.ArrayConverter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nurullah Erinola
 */
public class HelloRetryRequestParser extends HandshakeMessageParser<HelloRetryRequestMessage> {
    
    public HelloRetryRequestParser(int pointer, byte[] array, ProtocolVersion version) {
        super(pointer, array, HandshakeMessageType.HELLO_RETRY_REQUEST, version);
    }

    @Override
    protected void parseHandshakeMessageContent(HelloRetryRequestMessage msg) {
         parseProtocolVersion(msg);
         parseSelectedCiphersuite(msg);
        if (hasExtensionLengthField(msg)) {
            parseExtensionLength(msg);
            if (hasExtensions(msg)) {
                parseExtensionBytes(msg);
            }
        }
    }

    @Override
    protected HelloRetryRequestMessage createHandshakeMessage() {
        return new HelloRetryRequestMessage();
    }
    
    protected void parseProtocolVersion(HelloRetryRequestMessage message) {
        message.setProtocolVersion(parseByteArrayField(HandshakeByteLength.VERSION));
        LOGGER.debug("ProtocolVersion:" + ArrayConverter.bytesToHexString(message.getProtocolVersion().getValue()));
    }
    
    protected void parseSelectedCiphersuite(HelloRetryRequestMessage msg) {
        msg.setSelectedCipherSuite(parseByteArrayField(HandshakeByteLength.CIPHER_SUITE));
        LOGGER.debug("SelectedCipherSuite: " + ArrayConverter.bytesToHexString(msg.getSelectedCipherSuite().getValue()));
    }
    
    protected void parseExtensionLength(HelloRetryRequestMessage message) {
        message.setExtensionsLength(parseIntField(HandshakeByteLength.EXTENSION_LENGTH));
        LOGGER.debug("ExtensionLength:" + message.getExtensionsLength().getValue());
    }
    
    protected void parseExtensionBytes(HelloRetryRequestMessage message) {
        byte[] extensionBytes = parseByteArrayField(message.getExtensionsLength().getValue());
        message.setExtensionBytes(extensionBytes);
        LOGGER.debug("ExtensionBytes:" + ArrayConverter.bytesToHexString(extensionBytes, false));
        List<ExtensionMessage> extensionMessages = new LinkedList<>();
        int pointer = 0;
        while (pointer < extensionBytes.length) {
            ExtensionParser parser = ExtensionParserFactory.getExtensionParser(extensionBytes, pointer);
            extensionMessages.add(parser.parse());
            pointer = parser.getPointer();
        }
        message.setExtensions(extensionMessages);
    }
    
    protected boolean hasExtensionLengthField(HelloRetryRequestMessage message) {
        return message.getLength().getValue() + HandshakeByteLength.MESSAGE_TYPE
                + HandshakeByteLength.MESSAGE_LENGTH_FIELD > getPointer() - getStartPoint();
    }


    protected boolean hasExtensions(HelloRetryRequestMessage message) {
        return message.getExtensionsLength().getValue() > 0;
    }
}
