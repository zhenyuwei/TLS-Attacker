/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message.computations;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.biginteger.ModifiableBigInteger;
import de.rub.nds.modifiablevariable.bytearray.ModifiableByteArray;
import de.rub.nds.tlsattacker.core.config.Config;
import java.math.BigInteger;

public class DHEServerComputations extends KeyExchangeComputations {

    /**
     * dh modulus used for computations
     */
    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.PUBLIC_KEY)
    private ModifiableBigInteger modulus;

    /**
     * dh generator used for computations
     */
    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.PUBLIC_KEY)
    private ModifiableBigInteger generator;

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.KEY_MATERIAL)
    private ModifiableByteArray serverRandom;

    public DHEServerComputations() {
    }

    public ModifiableBigInteger getModulus() {
        return modulus;
    }

    public void setModulus(ModifiableBigInteger modulus) {
        this.modulus = modulus;
    }

    public ModifiableBigInteger getGenerator() {
        return generator;
    }

    public void setGenerator(ModifiableBigInteger generator) {
        this.generator = generator;
    }

    public void setModulus(BigInteger modulus) {
        this.modulus = ModifiableVariableFactory.safelySetValue(this.modulus, modulus);
    }

    public void setGenerator(BigInteger generator) {
        this.generator = ModifiableVariableFactory.safelySetValue(this.generator, generator);
    }

    public ModifiableByteArray getServerRandom() {
        return serverRandom;
    }

    public void setServerRandom(ModifiableByteArray serverRandom) {
        this.serverRandom = serverRandom;
    }

    public void setServerRandom(byte[] serverRandom) {
        this.serverRandom = ModifiableVariableFactory.safelySetValue(this.serverRandom, serverRandom);
    }

    @Override
    public void setSecretsInConfig(Config config) {
        config.setDefaultServerDhPrivateKey(getPrivateKey().getValue());
    }
}
