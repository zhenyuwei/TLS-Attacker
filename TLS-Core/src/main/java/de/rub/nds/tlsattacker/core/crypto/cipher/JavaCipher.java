/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.crypto.cipher;

import de.rub.nds.tlsattacker.core.constants.CipherAlgorithm;
import de.rub.nds.tlsattacker.core.exceptions.CryptoException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class JavaCipher implements EncryptionCipher, DecryptionCipher {

    private final CipherAlgorithm algorithm;

    private byte[] iv = null;

    private Cipher cipher = null;

    public JavaCipher(CipherAlgorithm algorithm, byte[] iv) {
        this.algorithm = algorithm;
        this.iv = iv;
    }

    public JavaCipher(CipherAlgorithm algorithm) {
        this.algorithm = algorithm;
        iv = null;
    }

    @Override
    public int getBlocksize() {
        return algorithm.getBlocksize();
    }

    @Override
    public byte[] encrypt(byte[] key, byte[] iv, byte[] someBytes) throws CryptoException {
        IvParameterSpec encryptIv = new IvParameterSpec(iv);
        try {
            cipher = Cipher.getInstance(algorithm.getJavaName());
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()), encryptIv);
            byte[] result = cipher.doFinal(someBytes);
            this.iv = cipher.getIV();
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] key, byte[] someBytes) throws CryptoException {
        try {
            if (cipher == null) {
                cipher = Cipher.getInstance(algorithm.getJavaName());
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()));
            }
            byte[] result = cipher.doFinal(someBytes);
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] key, byte[] iv, int tagLength, byte[] someBytes) throws CryptoException {
        GCMParameterSpec encryptIv = new GCMParameterSpec(tagLength, iv);
        try {
            cipher = Cipher.getInstance(algorithm.getJavaName());
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()), encryptIv);
            byte[] result = cipher.doFinal(someBytes);
            this.iv = cipher.getIV();
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] encrypt(byte[] key, byte[] iv, int tagLength, byte[] additionAuthenticatedData, byte[] someBytes)
            throws CryptoException {
        GCMParameterSpec encryptIv = new GCMParameterSpec(tagLength, iv);
        try {
            cipher = Cipher.getInstance(algorithm.getJavaName());
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()), encryptIv);
            cipher.updateAAD(additionAuthenticatedData);
            byte[] result = cipher.doFinal(someBytes);
            this.iv = cipher.getIV();
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] getIv() {
        return iv;
    }

    @Override
    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] iv, byte[] someBytes) throws CryptoException {
        IvParameterSpec decryptIv = new IvParameterSpec(iv);
        try {
            cipher = Cipher.getInstance(algorithm.getJavaName());
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()), decryptIv);
            byte[] result = cipher.doFinal(someBytes);
            this.iv = cipher.getIV();
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] someBytes) throws CryptoException {
        try {
            if (cipher == null) {
                cipher = Cipher.getInstance(algorithm.getJavaName());
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()));
            }
            byte[] result = cipher.doFinal(someBytes);
            return result;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] iv, int tagLength, byte[] someBytes) throws CryptoException {
        GCMParameterSpec decryptIv = new GCMParameterSpec(tagLength, iv);
        try {
            cipher = Cipher.getInstance(algorithm.getJavaName());
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()), decryptIv);
            byte[] result = cipher.doFinal(someBytes);
            this.iv = cipher.getIV();
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }

    @Override
    public byte[] decrypt(byte[] key, byte[] iv, int tagLength, byte[] additionAuthenticatedData, byte[] someBytes)
            throws CryptoException {
        GCMParameterSpec decryptIv = new GCMParameterSpec(tagLength, iv);
        try {
            cipher = Cipher.getInstance(algorithm.getJavaName());
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, algorithm.getJavaName()), decryptIv);
            cipher.updateAAD(additionAuthenticatedData);
            byte[] result = cipher.doFinal(someBytes);
            this.iv = cipher.getIV();
            return result;
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException ex) {
            throw new CryptoException("Could not initialize JavaCipher", ex);
        }
    }
}
