package io.github.maxmmin.sol.core.crypto.program;

import io.github.maxmmin.sol.core.crypto.Base58;
import io.github.maxmmin.sol.core.crypto.BytesUtil;
import io.github.maxmmin.sol.core.crypto.PublicKey;
import io.github.maxmmin.sol.core.crypto.transaction.TransactionInstruction;
import io.github.maxmmin.sol.program.SystemProgram;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.maxmmin.sol.program.SystemProgram.*;
import static org.junit.jupiter.api.Assertions.*;

public class SystemProgramTest {
    @Test
    public void testTransfer() {
        PublicKey fromPublicKey = PublicKey.fromBase58("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
        PublicKey toPublicKey = PublicKey.fromBase58("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
        BigInteger lamports = BigInteger.valueOf(3000);

        TransferParams transferParams = new TransferParams(fromPublicKey, toPublicKey, lamports);
        TransactionInstruction instruction = SystemProgram.transfer(transferParams);

        assertEquals(SystemProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(2, instruction.getAccounts().size());
        assertEquals(fromPublicKey, instruction.getAccounts().get(0).getPubkey());
        assertTrue(instruction.getAccounts().get(0).isSigner());
        assertTrue(instruction.getAccounts().get(0).isWritable());
        assertEquals(toPublicKey, instruction.getAccounts().get(1).getPubkey());
        assertFalse(instruction.getAccounts().get(1).isSigner());
        assertTrue(instruction.getAccounts().get(1).isWritable());

        byte[] expectedData = {2, 0, 0, 0, -72, 11, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expectedData, instruction.getData());
    }

    @Test
    public void testCreateAccount() {
        PublicKey fromPublicKey = PublicKey.fromBase58("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
        PublicKey newAccountPublicKey = PublicKey.fromBase58("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");
        BigInteger lamports = BigInteger.valueOf(2039280);
        BigInteger space = BigInteger.valueOf(165);


        CreateAccountParams createAccountParams = new CreateAccountParams(fromPublicKey, newAccountPublicKey, lamports, space, PROGRAM_ID);

        TransactionInstruction instruction = SystemProgram.createAccount(createAccountParams);

        assertEquals(SystemProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(2, instruction.getAccounts().size());
        assertEquals(fromPublicKey, instruction.getAccounts().get(0).getPubkey());
        assertTrue(instruction.getAccounts().get(0).isSigner());
        assertTrue(instruction.getAccounts().get(0).isWritable());
        assertEquals(newAccountPublicKey, instruction.getAccounts().get(1).getPubkey());
        assertTrue(instruction.getAccounts().get(1).isSigner());
        assertTrue(instruction.getAccounts().get(1).isWritable());

        String expectedDataBase58 = "11119os1e9qSs2u7TsThXqkBSRUo9x7kpbdqtNNbTeaxHGPdWbvoHsks9hpp6mb2ed1NeB";
        assertArrayEquals(Base58.decodeFromString(expectedDataBase58), instruction.getData());
    }

    @Test
    public void testAssignInstruction() {
        PublicKey owner = PublicKey.fromBase58("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo");
        PublicKey newOwner = PublicKey.fromBase58("GrDMoeqMLFjeXQ24H56S1RLgT4R76jsuWCd6SvXyGPQ5");

        AssignParams assignParams = new AssignParams(owner, newOwner);

        TransactionInstruction instruction = SystemProgram.assign(assignParams);

        assertEquals(SystemProgram.PROGRAM_ID, instruction.getProgramId());
        assertEquals(1, instruction.getAccounts().size());
        assertEquals(owner, instruction.getAccounts().get(0).getPubkey());
        assertTrue(instruction.getAccounts().get(0).isSigner());
        assertTrue(instruction.getAccounts().get(0).isWritable());

        byte[] expected = BytesUtil.allocateLE(36)
                .putInt(ASSIGN_INDEX)
                .put(newOwner.getBytes())
                .array();

        assertArrayEquals(expected, instruction.getData());
    }
}
