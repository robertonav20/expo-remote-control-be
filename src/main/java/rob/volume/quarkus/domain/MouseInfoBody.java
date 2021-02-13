package rob.volume.quarkus.domain;

import lombok.Data;

@Data
public class MouseInfoBody extends Response {
    public Integer x;
    public Integer y;
}
