package rob.volume.quarkus.domain;

import lombok.Data;

@Data
public class VolumeInfoBody extends Response {
    private Integer volume;
}
