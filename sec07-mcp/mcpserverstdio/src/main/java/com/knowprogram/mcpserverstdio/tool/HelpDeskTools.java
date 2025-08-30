package com.knowprogram.mcpserverstdio.tool;

import com.knowprogram.mcpserverstdio.entity.HelpDeskTicket;
import com.knowprogram.mcpserverstdio.model.TicketRequest;
import com.knowprogram.mcpserverstdio.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelpDeskTools {

        private final HelpDeskTicketService helpDeskTicketService;

        @Tool(name = "createTicket", description = "Create a new Support ticket")
        public String createTicket(
                        @ToolParam(required = true, description = "Details to create a Support ticket") TicketRequest ticketRequest,
                        ToolContext toolContext) {
                String username = (String) toolContext.getContext().get("username");
                log.info("Creating ticket for user: {}", username);
                HelpDeskTicket ticket = helpDeskTicketService.createTicket(ticketRequest, username);
                log.info("Ticket #{} created successfully for user: {}", ticket.getId(), username);
                return "Ticket #" + ticket.getId() + " created successfully for user: " + username;
        }

        @Tool(name = "getTicketStatus", description = "Fetch the status of the open tickets based on a given username")
        public List<HelpDeskTicket> getTicketStatus(ToolContext toolContext) {
                String username = (String) toolContext.getContext().get("username");
                log.info("Fetching ticket status for user: {}", username);
                List<HelpDeskTicket> tickets = helpDeskTicketService.getTicketsByUsername(username);
                log.info("Found {} tickets for user: {}", tickets.size(), username);
                return tickets;
        }
}
