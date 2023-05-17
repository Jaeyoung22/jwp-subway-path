package subway.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PathFinderTest {

    @Test
    @DisplayName("최단 경로를 조회한다.")
    public void getDijkstraShortestPath() {
        List<Section> sections = List.of(
            new Section(1L, 1L, 2L, 1L, 5),
            new Section(1L, 2L, 3L, 1L, 5),
            new Section(1L, 3L, 4L, 1L, 5),
            new Section(1L, 1L, 3L, 2L, 2)
        );
        PathFinder pathFinder = new PathFinder(sections);
        assertAll(
            () -> assertThat(pathFinder.findPath(1L, 4L)).containsExactly(1L, 3L, 4L),
            () -> assertThat(pathFinder.findTotalDistance(1L, 4L)).isEqualTo(7));
    }

}
