package com.example.demo.services.organization;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.organization.Menu;
import com.example.demo.repository.organization.MenuRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getList() {
        return menuRepository.findAll();
    }

    public Menu create(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu update(Menu menu, Menu menuFromDb, Long id) {
        if (!menuRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        BeanUtils.copyProperties(menu, menuFromDb, "id");
        return menuRepository.save(menuFromDb);
    }

    public void delete(Menu menuFromDb, Long id) {
        if (!menuRepository.existsById(id)) {
            throw new NotFoundException(id);
        }
        menuRepository.delete(menuFromDb);
    }
}
