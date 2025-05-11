package io.github.maxmmin.sol.core.client.type.response.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * @param <K> - account key type
 * @param <I> - instruction type
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedTransactionDetails<K,I> {
    @JsonProperty("message")
    public Message<K,I> message;

    @JsonProperty("signatures")
    public List<String> signatures;
}
