package org.mxmn.sol.core.type.response;

import lombok.Data;

@Data
public class SolanaNodeInfo {
    private Long featureSet;
    private String gossip;
    private String pubkey;
    private String pubsub;
    private String rpc;
    private String serveRepair;
    private Integer shredVersion;
    private String tpu;
    private String tpuForwards;
    private String tpuForwardsQuic;
    private String tpuQuic;
    private String tpuVote;
    private String tvu;
    private String version;
}
