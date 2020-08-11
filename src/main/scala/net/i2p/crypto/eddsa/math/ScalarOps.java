package net.i2p.crypto.eddsa.math;

public interface ScalarOps {
    /**
     * Reduce the given scalar mod l.
     * <p>
     * From the Ed25519 paper:<br>
     * Here we interpret 2b-bit strings in little-endian form as integers in
     * {0, 1,..., 2^(2b)-1}.
     *
     * @return s mod l
     */
    byte[] reduce(byte[] s);

    /**
     * r = (a * b + c) mod l
     *
     * @return (a * b + c) mod l
     */
    byte[] multiplyAndAdd(byte[] a, byte[] b, byte[] c);
}
