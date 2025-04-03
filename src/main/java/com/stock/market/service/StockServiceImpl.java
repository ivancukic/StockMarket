package com.stock.market.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stock.market.dto.StockDTO;
import com.stock.market.entity.Stock;
import com.stock.market.exception.BadRequestAlertException;
import com.stock.market.exception.ResourceNotFoundException;
import com.stock.market.exception.StockNotFoundException;
import com.stock.market.mapper.StockMapper;
import com.stock.market.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {
	
	private final StockRepository stockRepository;

	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}
	
	@Override
	public List<StockDTO> findAll() {
		return stockRepository.findAll()
                .stream()
                .map(StockMapper::convertToDto)
                .collect(Collectors.toList());
	}
	
	@Override
	public Optional<StockDTO> findById(Long id) {
		return stockRepository.findById(id)
								.map(StockMapper::convertToDto);
	}
	
	@Override
	@Transactional
	public StockDTO save(StockDTO dto) {
		Stock stock = StockMapper.convertToEntity(dto);
		
		Stock saved = stockRepository.save(stock);
		
		return StockMapper.convertToDto(saved);
	}
	
	@Override
	public StockDTO update(Long id, StockDTO dto) {

		if (dto.getId() == null || !Objects.equals(id, dto.getId())) {
	           throw new BadRequestAlertException("Stock ID mismatch: Provided ID does not match the entity ID.");
	    }
		
		Stock updatedStock = stockRepository.findById(id)
												.orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
		
		updatedStock.setCompanyName(dto.getCompanyName());
		updatedStock.setMarketName(dto.getMarketName());
		updatedStock.setFoundedDate(dto.getFoundedDate());
		updatedStock.setStockPrice(dto.getStockPrice());

	   updatedStock = stockRepository.save(updatedStock);

	    return StockMapper.convertToDto(updatedStock);
	}

	@Override
	public void delete(Long id) {
		if(!stockRepository.existsById(id)) {
			throw new StockNotFoundException("Stock with id " + id + " not found");
		}
		stockRepository.deleteById(id);
	}

	 @Override
	 public Optional<StockDTO> findByCompanyName(String companyName) {
	  Stock stock = stockRepository.findByCompanyName(companyName);
		 if (stock == null) {
	            return Optional.empty();
	     }
	  StockDTO dto = StockMapper.convertToDto(stock);
	  return Optional.of(dto);
    }

}
