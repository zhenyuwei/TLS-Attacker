/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator;

import de.rub.nds.tlsattacker.core.protocol.preparator.AlertPreparator;
import de.rub.nds.tlsattacker.core.constants.AlertDescription;
import de.rub.nds.tlsattacker.core.constants.AlertLevel;
import de.rub.nds.tlsattacker.core.protocol.message.AlertMessage;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class AlertPreparatorTest {

    private AlertMessage message;
    private TlsContext context;
    private AlertPreparator preparator;

    public AlertPreparatorTest() {
    }

    @Before
    public void setUp() {
        message = new AlertMessage();
        context = new TlsContext();
        preparator = new AlertPreparator(context, message);
    }

    /**
     * Test of prepareProtocolMessageContents method, of class AlertPreparator.
     */
    @Test
    public void testPrepare() {
        message.setConfig(AlertLevel.FATAL, AlertDescription.DECRYPT_ERROR);
        preparator.prepare();
        assertTrue(message.getLevel().getValue() == AlertLevel.FATAL.getValue());
        assertTrue(message.getDescription().getValue() == AlertDescription.DECRYPT_ERROR.getValue());
    }

}