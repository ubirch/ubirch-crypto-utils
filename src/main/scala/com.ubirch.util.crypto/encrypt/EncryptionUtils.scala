package com.ubirch.util.crypto.encrypt

import com.ubirch.util.crypto.encrypt.crypto.JavaCryptoEncryption

object AES extends JavaCryptoEncryption()

object DES extends JavaCryptoEncryption("DES")


