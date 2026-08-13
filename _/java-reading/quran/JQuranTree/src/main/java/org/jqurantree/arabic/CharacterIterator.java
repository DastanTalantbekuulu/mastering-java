package org.jqurantree.arabic;

import org.jqurantree.core.collections.ImmutableIteratorBase;

class CharacterIterator extends ImmutableIteratorBase<ArabicCharacter> {

	private final byte[] buffer;
	private final int maxOffset;
	private int offset;

	public CharacterIterator(byte[] buffer, int offset, int characterCount) {
		this.buffer = buffer;
		this.offset = offset;
		this.maxOffset = offset + characterCount * ByteFormat.CHARACTER_WIDTH;
	}

	public boolean hasNext() {
		return offset < maxOffset;
	}

	public ArabicCharacter next() {
		ArabicCharacter character = new ArabicCharacter(buffer, offset);
		offset += ByteFormat.CHARACTER_WIDTH;
		return character;
	}
}
