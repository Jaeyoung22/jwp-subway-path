package subway.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import subway.dao.StationDao;
import subway.domain.Station;
import subway.dto.StationRequest;
import subway.dto.StationResponse;
import subway.exception.DomainException;
import subway.exception.ExceptionType;

@Transactional(readOnly = true)
@Service
public class StationService {
    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Transactional
    public StationResponse saveStation(StationRequest stationRequest) {
        String name = stationRequest.getName();
        if (stationDao.findByName(name) != null) {
            throw new DomainException(ExceptionType.STATION_ALREADY_EXIST);
        }
        Station station = stationDao.insert(new Station(name));
        return StationResponse.of(station);
    }

    public StationResponse findStationResponseById(Long id) {
        return StationResponse.of(stationDao.findById(id));
    }

    public List<StationResponse> findAllStationResponses() {
        List<Station> stations = stationDao.findAll();

        return stations.stream()
            .map(StationResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateStation(Long id, StationRequest stationRequest) {
        if (stationDao.findById(id) == null) {
            throw new DomainException(ExceptionType.STATION_DOES_NOT_EXIST);
        }
        stationDao.update(new Station(id, stationRequest.getName()));
    }

    @Transactional
    public void deleteStationById(Long id) {
        if (stationDao.findById(id) == null) {
            throw new DomainException(ExceptionType.STATION_DOES_NOT_EXIST);
        }
        stationDao.deleteById(id);
    }
}
