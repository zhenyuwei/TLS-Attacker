/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.serializer.extension;

import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.serializer.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 * @param <T>
 */
public abstract class ExtensionSerializer<T extends ExtensionMessage> extends Serializer {

    private ExtensionMessage message;

    public ExtensionSerializer(T message) {
        super();
        this.message = message;
    }

    @Override
    protected byte[] serializeBytes() {
        writeType();
        writeLength();
        serializeExtensionContent();

        return getAlreadySerialized();
    }

    private void writeType() {
        appendBytes(message.getExtensionType().getValue());
    }

    private void writeLength() {
        appendInt(message.getExtensionLength().getValue(), ExtensionByteLength.EXTENSIONS_LENGTH);
    }

    public abstract byte[] serializeExtensionContent();
}