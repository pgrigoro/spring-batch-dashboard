package com.bmo.cm.sds.web.controller.websocket;

import com.bmo.cm.sds.web.dao.DashboardDao;
import com.bmo.cm.sds.web.model.DashboardEntry;
import com.bmo.cm.sds.web.model.WebsocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by matt on 29/11/2015.
 * TODO:Refine query returning dashboard entries, perhaps we could fetch entries 12/24/36/48 hours back.
 */
@Controller
public class DashboardController {

    DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DashboardDao dao;

    @MessageMapping("/dashboard")
    @SendTo("/topic/dashboardEntries")
    public List<DashboardEntry> pushEntries(WebsocketMessage m) throws Exception {
        return dao.findByFromDateBetween(LocalDate.parse(m.getDate(),dTF).atStartOfDay(), LocalDate.parse(m.getDate(),dTF).plusDays(1).atStartOfDay());
    }
}
