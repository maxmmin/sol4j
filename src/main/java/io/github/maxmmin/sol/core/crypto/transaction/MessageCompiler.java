package io.github.maxmmin.sol.core.crypto.transaction;

public class MessageCompiler {
    private final Message message;

    public MessageCompiler(Message message) {
        this.message = message;
    }

    public byte[] compile() {

    }


    protected int calculateInstructionSize(CompiledInstruction instruction) {
        return 1 + instruction.getAccountsSizeU16().getBytesCount() + instruction.getAccounts().length
                + instruction.getDataSizeU16().getBytesCount() + instruction.getData().length;
    }

    public static final int HEADER_BYTES = 3;
    public static final int SIGNATURE_BYTES = 64;
    public static final int BLOCKHASH_BYTES = 32;
}
