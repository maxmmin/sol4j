package io.github.maxmmin.sol.core.client.type.response.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
