package com.changhf.service.user.impl;

import com.changhf.common.Constants;
import com.changhf.common.ResponseMessageMap;
import com.changhf.dao.user.UserDeptDao;
import com.changhf.domain.UserDepartment;
import com.changhf.domain.tree.TreeNode;
import com.changhf.service.user.UserDeptService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("userDeptService")
public class UserDeptServiceImpl implements UserDeptService {

    @Autowired
    protected UserDeptDao userDeptDao;

    @Override
    public Map<String, Object> getDepTreeByDepth(Integer depth, Integer roleEntId, Integer roleDepId) {
        Map<String, Object> rtnMap = Maps.newHashMap();
        List<TreeNode> rtnNodeList = new ArrayList<TreeNode>();
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        //获取所有部门列表,转换为TreeNode
        List<UserDepartment> deptList = getAllDepts();
        long start = System.currentTimeMillis();
        if (!deptList.isEmpty()) {
            for (UserDepartment dept : deptList) {
                TreeNode treeNode = new TreeNode();
                treeNode.setId(dept.getDepartmentId());
                treeNode.setParentId(dept.getDepartmentParentid());
                treeNode.setText(dept.getDepartmentName());
                treeNode.setDepth(dept.getDepartmentDepth());
                treeNodeList.add(treeNode);
            }
            for (TreeNode node1 : treeNodeList) {
                boolean flag = false;
				for (TreeNode node2 : treeNodeList) {
					if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
						flag = true;
						if (node2.getChildren() == null) {
							node2.setChildren(new ArrayList<>());
						}
						node2.getChildren().add(node1);
						break;
					}
				}
				if (!flag) {
					rtnNodeList.add(node1);
				}
			}
        }
        System.out.println(System.currentTimeMillis() - start);
        rtnMap.put(Constants.LIST, rtnNodeList);
        return ResponseMessageMap.successMap(rtnMap);
    }

    private List<UserDepartment> getAllDepts() {
        return userDeptDao.getDeptRootList();
    }
}
