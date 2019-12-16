#include <stdio.h>
#include <stdlib.h>

char setMemoryBlock(char *str, char *memBlock, int &blockNum, int &bitNum, char *type) {

}

char setBit(char c, int pos) {
	
	c |= 1 << pos;
	return c;
}

int main() {

	char *memBlock = (char*)calloc(sizeof(char)*3);
	char *str1 = "1110";
	char *str2 = "00";

	int blockNum = 0;
	int bitNum = 0;

	setMemoryBlock(str, memBlock, blockNum, bitNum, "one");
	setMemoryBlock(str, memBlock, blockNum, bitNum, "byte");
	setMemoryBlock(str, memBlock, blockNum, bitNum, "zero");
	setMemoryBlock(str, memBlock, blockNum, bitNum, "one");
}