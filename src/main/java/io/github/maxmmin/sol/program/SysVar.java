package io.github.maxmmin.sol.program;

import io.github.maxmmin.sol.core.crypto.PublicKey;

public class SysVar {
    public static final PublicKey CLOCK_PUBKEY = PublicKey.fromBase58(
            "SysvarC1ock11111111111111111111111111111111"
    );

    public static final PublicKey EPOCH_SCHEDULE_PUBKEY = PublicKey.fromBase58(
            "SysvarEpochSchedu1e111111111111111111111111"
    );

    public static final PublicKey INSTRUCTIONS_PUBKEY = PublicKey.fromBase58(
            "Sysvar1nstructions1111111111111111111111111"
    );

    public static final PublicKey RECENT_BLOCKHASHES_PUBKEY = PublicKey.fromBase58(
            "SysvarRecentB1ockHashes11111111111111111111"
    );

    public static final PublicKey RENT_PUBKEY = PublicKey.fromBase58(
            "SysvarRent111111111111111111111111111111111"
    );

    public static final PublicKey REWARDS_PUBKEY = PublicKey.fromBase58(
            "SysvarRewards111111111111111111111111111111"
    );

    public static final PublicKey SLOT_HASHES_PUBKEY = PublicKey.fromBase58(
            "SysvarS1otHashes111111111111111111111111111"
    );

    public static final PublicKey SLOT_HISTORY_PUBKEY = PublicKey.fromBase58(
            "SysvarS1otHistory11111111111111111111111111"
    );

    public static final PublicKey STAKE_HISTORY_PUBKEY = PublicKey.fromBase58(
            "SysvarStakeHistory1111111111111111111111111"
    );
}
