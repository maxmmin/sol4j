package io.github.maxmmin.sol.core.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @param <K> - account key type
 * @param <I> - instruction type
 */
@Data
public class TransactionDetails<K,I> {
    @JsonProperty("message")
    public Message<K,I> message;

    @JsonProperty("signatures")
    public List<String> signatures;
}
