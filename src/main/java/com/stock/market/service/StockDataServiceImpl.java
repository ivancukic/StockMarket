package com.stock.market.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stock.market.dto.PeriodDTO;
import com.stock.market.dto.ResponseDTO;
import com.stock.market.dto.StockDTO;
import com.stock.market.dto.StockDataDTO;
import com.stock.market.entity.Stock;
import com.stock.market.entity.StockData;
import com.stock.market.exception.BadRequestAlertException;
import com.stock.market.exception.ResourceNotFoundException;
import com.stock.market.exception.StockNotFoundException;
import com.stock.market.mapper.StockDataMapper;
import com.stock.market.mapper.StockMapper;
import com.stock.market.repository.StockDataRepository;
import com.stock.market.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockDataServiceImpl implements StockDataService {
	
	private final StockDataRepository stockDataRepository;
	private final StockRepository stockRepository;
	
	
	public StockDataServiceImpl(StockDataRepository stockDataRepository, StockRepository stockRepository) {
		this.stockDataRepository = stockDataRepository;
		this.stockRepository = stockRepository;
	}

	@Override
	public List<StockDataDTO> findAll() {
		return stockDataRepository.findAll()
                .stream()
                .map(StockDataMapper::convertToDto)
                .collect(Collectors.toList());
	}
	
	@Override
	public Optional<StockDataDTO> findById(Long id) {
		return stockDataRepository.findById(id)
								.map(StockDataMapper::convertToDto);
	}
	
	@Override
	@Transactional
	public StockDataDTO save(StockDataDTO dto) {
		Stock stock = stockRepository.findById(dto.getStock().getId())
										.orElseThrow(() -> new StockNotFoundException("Stock not found"));
		
		StockData stockData = StockDataMapper.convertToEntity(dto, stock);
		
		StockData saved = stockDataRepository.save(stockData);
		
		return StockDataMapper.convertToDto(saved);
	}
	
	@Override
	public StockDataDTO update(Long id, StockDataDTO dto) {

		if (dto.getId() == null || !Objects.equals(id, dto.getId())) {
	           throw new BadRequestAlertException("Stock Data ID mismatch: Provided ID does not match the entity ID.");
	    }
		
		StockData updatedStockData = stockDataRepository.findById(id)
												.orElseThrow(() -> new ResourceNotFoundException("Stock Data not found with id: " + id));
		
		updatedStockData.setDate(dto.getDate());
		updatedStockData.setOpen(dto.getOpen());
		updatedStockData.setHigh(dto.getHigh());
		updatedStockData.setLow(dto.getLow());
		updatedStockData.setClose(dto.getClose());
		updatedStockData.setAdjClose(dto.getAdjClose());
		updatedStockData.setVolume(dto.getVolume());

		updatedStockData = stockDataRepository.save(updatedStockData);

	    return StockDataMapper.convertToDto(updatedStockData);
	}

	@Override
	public void delete(Long id) {
		if(!stockDataRepository.existsById(id)) {
			throw new StockNotFoundException("Stock Data with id " + id + " not found");
		}
		stockDataRepository.deleteById(id);
	}

	@Override
	public List<StockDataDTO> findStockDataByStock(StockDTO dto) {
		
		Stock stock = StockMapper.convertToEntity(dto);
		
		return stockDataRepository.findStockDataByStock(stock)
									.stream()
									.map(StockDataMapper::convertToDto)
									.collect(Collectors.toList());
	}

	@Override
	public ResponseDTO getCompanyStockForThreePeriods(String companyName, LocalDate startDateAsked, LocalDate endDateAsked) {
		
		long daysBetween = ChronoUnit.DAYS.between(startDateAsked, endDateAsked);
		
		LocalDate startDatePrevious = startDateAsked.minusDays(daysBetween);
		LocalDate endDatePrevious = startDateAsked.minusDays(1);
		
		LocalDate startDateNext = endDateAsked.plusDays(1);
		LocalDate endDateNext = endDateAsked.plusDays(daysBetween);
		
		List<StockDataDTO> previousList = getCompanyStockByDate(companyName, startDatePrevious, endDatePrevious);
		List<StockDataDTO> askedList = getCompanyStockByDate(companyName, startDateAsked, endDateAsked);
		List<StockDataDTO> nextList = getCompanyStockByDate(companyName, startDateNext, endDateNext);
		
		ResponseDTO response = new ResponseDTO();
		
		response.setPrevious(getPeriodData(previousList));
		response.setAsked(getPeriodData(askedList));
		response.setNext(getPeriodData(nextList));

		return response;
	}
	
	public List<StockDataDTO> getCompanyStockByDate(String companyName, LocalDate startDate, LocalDate endDate) {
		
		Stock stock = stockRepository.findByCompanyName(companyName);
		
		if (stock == null) {
            return new java.util.ArrayList<>();
        }
		
		return stockDataRepository.findByStockAndDateBetweenOrderByDate(stock, startDate, endDate)
											.stream()
											.map(StockDataMapper::convertToDto)
											.collect(Collectors.toList());
	}
	
	public PeriodDTO getPeriodData(List<StockDataDTO> stockDataDTOList) {
		
		if(stockDataDTOList==null || stockDataDTOList.isEmpty()) {
			return null;
		} 
		
		StockDataDTO minCloseDto = stockDataDTOList.get(0);
		StockDataDTO maxCloseDto = stockDataDTOList.get(0);
		
		for(StockDataDTO dto : stockDataDTOList) {
			
			if(dto.getClose() < minCloseDto.getClose()) {
				minCloseDto = dto;
			}
			if(dto.getClose() > maxCloseDto.getClose()) {
				maxCloseDto = dto;
			}
		}
		
		PeriodDTO periodDTO = new PeriodDTO();
		
		periodDTO.setMinDate(minCloseDto.getDate());
		periodDTO.setMinPrice(minCloseDto.getClose());
		periodDTO.setMaxDate(maxCloseDto.getDate());
		periodDTO.setMaxPrice(maxCloseDto.getClose());
		periodDTO.setProfitByDate(maxCloseDto.getClose() - minCloseDto.getClose());
		periodDTO.setMaxProfitByDate(maxProfitByDate(stockDataDTOList));
		
		return periodDTO;
		
	}
	
	public double maxProfitByDate(List<StockDataDTO> stockDataDTOList) {
		
		if(stockDataDTOList==null || stockDataDTOList.isEmpty()) {
			return 0;
		} 
		
		double maxProfit = 0;
		
		for(int i=0; i<stockDataDTOList.size(); i++) {
			double buy = stockDataDTOList.get(i).getClose();
			
			for(int j=i+1; j<stockDataDTOList.size(); j++) {
				double sell = stockDataDTOList.get(j).getClose();
				
				if(sell > buy) {
					maxProfit += sell - buy; 
				}
			}
		}
		return maxProfit;
	}
	
}
