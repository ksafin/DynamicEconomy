package couk.Adamki11s.Extras.Cryptography;

public abstract class CryptographyMethods {

	/**
	 * Converts the given string into a 128-bit MD5 hash value.
	 * @param s The string to encode.
	 * @return Returns the 128-bit hash value of the string.
	 */
	public abstract String computeMD5Hash(String s);
	
	/**
	 * Checks whether two hashes match each other.
	 * @param hash1 Hash 1.
	 * @param hash2 Hash 2.
	 * @return A boolean value indicating whether 2 hashes match or not.
	 */
	public abstract boolean compareHashes(String hash1, String hash2);
	
	/**
	 * Converts the given string into a 160-bit SHA1 hash value.
	 * @param s The sting to encode.
	 * @return Returns the 160-bit hash value of the string.
	 */
	public abstract String computeSHA1Hash(String s);
	
	/**
	 * Converts the given string into a 256-bit SHA2 hash value.
	 * @param s The sting to encode.
	 * @return Returns the 256-bit hash value of the string.
	 */
	public abstract String computeSHA2_256BitHash(String s);

	/**
	 * Converts the given string into a 384-bit SHA2 hash value.
	 * @param s The sting to encode.
	 * @return Returns the 384-bit hash value of the string.
	 */
	public abstract String computeSHA2_384BitHash(String s);

	/**
	 * Converts the given string into a 512-bit SHA2 hash value.
	 * @param s The sting to encode.
	 * @return Returns the 512-bit hash value of the string.
	 */
	public abstract String computeSHA2_512BitHash(String s);
	
}
