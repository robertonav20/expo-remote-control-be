package rob.volume.quarkus.domain;

import lombok.Data;

@Data
public class MouseBody {
    public Integer xCoordinate;
    public Integer yCoordinate;
    public Double pressure;
    public Boolean leftClick;
    public Boolean rightClick;
}
