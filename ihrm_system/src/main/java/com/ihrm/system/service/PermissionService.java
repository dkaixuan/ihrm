package com.ihrm.system.service;

import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.BeanMapUtils;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.PermissionApi;
import com.ihrm.domain.system.PermissionMenu;
import com.ihrm.domain.system.PermissionPoint;
import com.ihrm.system.dao.PermissionApiDao;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.PermissionMenuDao;
import com.ihrm.system.dao.PermissionPointDao;
import org.apache.commons.beanutils.BeanMap;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: 846602483
 * @Date: 2019-8-4 20:16
 * @Version 1.0
 */
@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private PermissionMenuDao permissionMenuDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private PermissionApiDao permissionApiDao;

    @Autowired
    private IdWorker idWorker;


    public void save(Map<String, Object> map) throws Exception {
        String id = idWorker.nextId() + "";
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        permission.setId(id);
        Integer type = permission.getType();
        switch (type) {
            case PermissionConstants.PY_MENU:
                PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                permissionMenu.setId(id);
                permissionMenuDao.save(permissionMenu);
                break;
            case PermissionConstants.PY_POINT:
                PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                permissionPoint.setId(id);
                permissionPointDao.save(permissionPoint);
                break;
            case PermissionConstants.PY_API:
                PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                permissionApi.setId(id);
                permissionApiDao.save(permissionApi);
                break;
            default:
                throw new CommonException(ResultCode.FAIL);
        }
        permissionDao.save(permission);

    }

    public void update(Map<String, Object> map) throws Exception {
        Permission permission = BeanMapUtils.mapToBean(map, Permission.class);
        Optional<Permission> optional = permissionDao.findById(permission.getId());
        if (optional.isPresent()) {
            Permission permissionFromDb = optional.get();
            permissionFromDb.setName(permission.getName());
            permissionFromDb.setCode(permission.getCode());
            permissionFromDb.setDescription(permission.getDescription());
            permissionFromDb.setEnVisible(permission.getEnVisible());
            Integer type = permissionFromDb.getType();
            switch (type) {
                case PermissionConstants.PY_MENU:
                    PermissionMenu permissionMenu = BeanMapUtils.mapToBean(map, PermissionMenu.class);
                    permissionMenu.setId(permissionFromDb.getId());
                    permissionMenuDao.save(permissionMenu);
                    break;
                case PermissionConstants.PY_POINT:
                    PermissionPoint permissionPoint = BeanMapUtils.mapToBean(map, PermissionPoint.class);
                    permissionPoint.setId(permissionFromDb.getId());
                    permissionPointDao.save(permissionPoint);
                    break;
                case PermissionConstants.PY_API:
                    PermissionApi permissionApi = BeanMapUtils.mapToBean(map, PermissionApi.class);
                    permissionApi.setId(permissionFromDb.getId());
                    permissionApiDao.save(permissionApi);
                    break;
                default:
                    throw new CommonException(ResultCode.FAIL);
            }
            permissionDao.save(permission);
        }

    }

    public List<Permission> findAll(Map map) throws Exception {
        Specification<Permission> specification=new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("pid"))) {
                    list.add(cb.equal(root.get("pid").as(String.class), map.get("pid")));
                }
                if (!StringUtils.isEmpty(map.get("enVisible"))) {
                    list.add(cb.equal(root.get("enVisible").as(String.class), map.get("enVisible")));
                }
                if (!StringUtils.isEmpty(map.get("type"))) {
                  String type= (String) map.get("type");
                    CriteriaBuilder.In<Object> in = cb.in(root.get("type"));
                    if ("0".equals(type)) {
                        in.value(1).value(2);
                    }else {
                        in.value(Integer.parseInt(type));
                    }
                    list.add(in);
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return permissionDao.findAll(specification);
    }

    public Map<String, Object> findById(String id) {
        Optional<Permission> optional = permissionDao.findById(id);
        Object object=null;
        Permission permission=null;
        if (optional.isPresent()) {
             permission = optional.get();
            Integer type = permission.getType();
            if (type == PermissionConstants.PY_MENU) {
                object = permissionMenuDao.findById(id).get();
            } else if (type == PermissionConstants.PY_POINT) {
                object = permissionPointDao.findById(id).get();
            } else if (type == PermissionConstants.PY_API) {
                object = permissionApiDao.findById(id).get();
            }
        }
        Map<String, Object> map = BeanMapUtils.beanToMap(object);
        map.put("name", permission.getName());
        map.put("type", permission.getType());
        map.put("code", permission.getCode());
        map.put("description", permission.getDescription());
        map.put("pid", permission.getPid());

        return map;
    }

    public void deleteById(String id) {
        permissionDao.deleteById(id);
        Optional<Permission> optional = permissionDao.findById(id);
        if (optional.isPresent()) {
            Permission permission = optional.get();
            Integer type = permission.getType();
            switch (type) {
                case PermissionConstants.PY_MENU:
                    permissionMenuDao.deleteById(id);
                    break;
                case PermissionConstants.PY_POINT:
                    permissionPointDao.deleteById(id);
                    break;
                case PermissionConstants.PY_API:
                    permissionApiDao.deleteById(id);
                    break;
                default:
                    throw new CommonException(ResultCode.FAIL);
            }
        }
    }
}
