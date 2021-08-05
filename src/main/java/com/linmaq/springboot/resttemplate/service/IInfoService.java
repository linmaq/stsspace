package com.linmaq.springboot.resttemplate.service;

import java.net.URISyntaxException;

/**
 * @Class : IInfoService
 * @author: marin
 * @date : 9:46 PM 8/5/2021
 */
public interface IInfoService {
    /**
     * @return infos
     */
    String getInfos() throws URISyntaxException;
}
