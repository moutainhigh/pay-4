package com.pay.app.controller.base.message;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.dto.MessageContextDTO;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.dto.MessageSendDTO;
import com.pay.app.service.messagebox.MessageContextService;
import com.pay.app.service.messagebox.MessageReceiveService;
import com.pay.app.service.messagebox.MessageSendService;
import com.pay.util.DateUtil;


/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-24 下午05:50:07 站内信相关
 */
@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
public class MessageController extends MultiActionController {

	private String messageListPage;
	private String messageDetailPage;
	private MessageReceiveService messageReceiveService;
	private MessageContextService messageContextService;
	private MessageSendService messageSendService;
	private String errorMv;
	
	public void setErrorMv(String errorMv) {
        this.errorMv = errorMv;
    }

    public void setMessageSendService(MessageSendService messageSendService) {
    	this.messageSendService = messageSendService;
    }

	public void setMessageContextService(
			MessageContextService messageContextService) {
		this.messageContextService = messageContextService;
	}

	public void setMessageDetailPage(String messageDetailPage) {
		this.messageDetailPage = messageDetailPage;
	}

	public void setMessageReceiveService(
			MessageReceiveService messageReceiveService) {
		this.messageReceiveService = messageReceiveService;
	}

	public void setMessageListPage(String messageListPage) {
		this.messageListPage = messageListPage;
	}

	/**
	 * 根据个人的userId来查询出查看个人所有站内信
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MESSGE_QUERY")
	public ModelAndView messgeList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageReceiveDTO messageReceiveDto = new MessageReceiveDTO();
		LoginSession loginSession = SessionHelper.getLoginSession();
		int totalCount = 0;
		int pager_offset = 1;
		int page_size = 5;
		int countUnRead=0;
		try{
			pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); 
		}catch(NumberFormatException e){
			pager_offset = 1;
		}
		PageUtil pu = null;
		if(loginSession!=null && loginSession.getMemberCode()!=null){
			String memberCode = loginSession.getMemberCode();
			countUnRead = (Integer) messageReceiveService.countUnRead(memberCode);// 未读消息数
			totalCount = messageReceiveService.countMessageReceive(memberCode);
			pu = new PageUtil(pager_offset, page_size, totalCount);
			messageReceiveDto.setUserId(memberCode);
		}
		int beginNum = pu.getStartIndex();
		messageReceiveDto.setBeginNum(beginNum);
		messageReceiveDto.setEndNum(beginNum + page_size);
		List<MessageReceiveDTO> list = messageReceiveService.queryMessageListByPage(messageReceiveDto);

		ModelAndView mv = new ModelAndView(messageListPage);
		mv.addObject("messageList",list);
		mv.addObject("pu",pu);
		mv.addObject("countUnRead",countUnRead);
		return mv;
	}
	
	/**
	 * 站内信的详细信息
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MESSGE_QUERY")
	public ModelAndView messageDetail(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		LoginSession loginSession = (LoginSession) req.getSession().getAttribute("userSession");
		String memberCode = loginSession.getMemberCode();//memberCode
		String mcidHidden = req.getParameter("mcidHidden");//msgcontextId
		String rn   = req.getParameter("rn");//当前rownum
		MessageSendDTO sendDto=messageSendService.queryMessageSendByContextId(Long.parseLong(mcidHidden));
		MessageReceiveDTO receiveDto=messageReceiveService.queryMessageReceiveByContextId(Long.parseLong(mcidHidden),memberCode);
		if(null == receiveDto){
		    return new ModelAndView("redirect:/404.html");
        }
//		String stHidden=null;
//		if(null!=sendDto && null!=sendDto.getSendTime()){
//			stHidden = sendDto.getSendTime().toString();//req.getParameter("stHidden");//sendTime
//		}
		String idHidden=null;
		if(null!=receiveDto && null!=receiveDto.getId()){
			 idHidden = receiveDto.getId().toString();//req.getParameter("idHidden");//receiveId
		}
		String sendName =sendDto.getSendId();// new String(req.getParameter("sendName").getBytes("iso-8859-1"),"utf-8");//发件人
		String title =sendDto.getTitle();//  new String(req.getParameter("title").getBytes("iso-8859-1"),"utf-8");//标题
		//查询出当前数据后，检测是否有上一条下一条数据
		//更新读取状态
		if(StringUtils.isNotEmpty(idHidden)){
			messageReceiveService.updateById(idHidden);
		}
		//获取详情内容
		MessageContextDTO dto = this.findMsgContext(Long.valueOf(mcidHidden));
		//查询总数
		int num = messageReceiveService.countByMemberCode(memberCode);
		//上一条rownum
		Long orn  = null;
		//下一条rownum
		Long nrn  = null;
		//上一条逻辑
		try{
			if(Long.parseLong(rn)>1){//大于第一条
				orn = Long.parseLong(rn)-1; 
			}
			else if(Long.parseLong(rn)==1){//等于第一条
				orn  = Long.valueOf(0);
			}else{
				orn  = Long.valueOf(0);
			}
		}catch(NumberFormatException e){
			orn = Long.valueOf(0);
		}
		//下一条逻辑
		try{
			if(Long.parseLong(rn)<num){//小于最后一条
				nrn = Long.parseLong(rn)+1;
			}
			else if(Long.parseLong(rn)==num){
				nrn =  Long.valueOf(0);
			}else{
				nrn =  Long.valueOf(0);
			}
		}catch(NumberFormatException e){
			nrn = Long.valueOf(0);
		}
		Date sendtime = sendDto.getSendTime();
		String sendDate = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",sendtime);
		ModelAndView mv = new ModelAndView(messageDetailPage);
		mv.addObject("detail", dto);
		mv.addObject("sendtime", sendDate);
		mv.addObject("mcid",mcidHidden);
		mv.addObject("id",idHidden);
		mv.addObject("sendName",sendName);
		mv.addObject("title",title);
		mv.addObject("orn",orn);
		mv.addObject("nrn",nrn);
		return mv;
	}
	/**
	 * 查询MSG的上一条下一条
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MESSGE_QUERY")
	public ModelAndView queryMsgByRn(HttpServletRequest req, HttpServletResponse resp)
	 {
		//根据ROWNUM （rn）查询上一条、下一条详情
		String orn = req.getParameter("orn");
		String nrn = req.getParameter("nrn");
		String flag = req.getParameter("flag");
		LoginSession loginSession = (LoginSession) req.getSession().getAttribute("userSession");
		String memberCode = loginSession.getMemberCode();//memberCode
		MessageReceiveDTO mrdto = null;
		try{
			if("1".equals(flag)){//上一条
				mrdto = messageReceiveService.queryByRn(Long.valueOf(orn), memberCode);
			}else if("2".equals(flag)){//下一条
				mrdto = messageReceiveService.queryByRn(Long.valueOf(nrn), memberCode);
			}else{
				mrdto = messageReceiveService.queryByRn(Long.valueOf(1), memberCode);
			}
		}catch(NumberFormatException e){
			mrdto = messageReceiveService.queryByRn(Long.valueOf(1), memberCode);
		}
		if(null!=mrdto.getId()){
			messageReceiveService.updateById(mrdto.getId()+"");
		}
		//获取详情内容
		MessageContextDTO mcdto = this.findMsgContext(mrdto.getMessageContextId());
		
		//查询出当前数据后，检测是否有上一条下一条数据
		Long rn = mrdto.getRn();//当前数据的rownum
		//查询总数
		int num = messageReceiveService.countByMemberCode(memberCode);
		//上一条rownum
		Long norn  = null;
		//下一条rownum
		Long nnrn  = null;
		//上一条逻辑
		if(rn>1){//大于第一条
			norn = rn-1; 
		}
		else if(rn==1){//等于第一条
			norn  = Long.valueOf(0);
		}else{
			norn  = Long.valueOf(0);
		}
		//下一条逻辑
		if(rn<num){//小于最后一条
			nnrn = rn+1;
		}
		else if(rn==num){
			nnrn =  Long.valueOf(0);
		}else{
			nnrn =  Long.valueOf(0);
		}
		
		Date sendtime = mrdto.getSendTime();
		String sendDate = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",sendtime);
		ModelAndView mv = new ModelAndView(messageDetailPage);
		mv.addObject("detail", mcdto);//详情DTO
		mv.addObject("sendtime", sendDate);
		mv.addObject("mcid",mcdto.getId());
		mv.addObject("id",mrdto.getId());
		mv.addObject("sendName",mrdto.getSendName());
		mv.addObject("title",mrdto.getTitle());
		mv.addObject("orn",norn);
		mv.addObject("nrn",nnrn);
		return mv;
	}
	
	
	/**
	 * 根据ID查询消息内容
	 * @param id
	 * @return
	 */
	private MessageContextDTO findMsgContext(Long id){
		return messageContextService.findMessageContext(new Long(id));
	}
	
	/**
	 * 单一删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MESSGE_DELETE")
	public ModelAndView delMessage(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String idHidden = req.getParameter("idHidden");
		String mcidHidden = req.getParameter("mcidHidden");
		MessageReceiveDTO messageReceiveDto = new MessageReceiveDTO();
		try{
			messageReceiveDto.setId(new Long(idHidden));
		}catch(Exception e){
			return this.messgeList(req, resp);
		}
		messageReceiveDto.setMessageContextId(new Long(mcidHidden));
		messageReceiveService.delMessageReceive(messageReceiveDto);
		return this.messgeList(req, resp);
	}

	/**
	 * 批量删除
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MESSGE_DELETE")
	public ModelAndView delAllMessage(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String[] messageId = req.getParameterValues("messageId");
		String[] mcidHidden = req.getParameterValues("mcidHidden");
		MessageReceiveDTO messageReceiveDto = new MessageReceiveDTO();
		if (null != messageId && messageId.length > 0) {
			for (int i = 0; i < messageId.length; i++) {
				try{
					messageReceiveDto.setId(new Long(messageId[i]));
					messageReceiveDto.setMessageContextId(new Long(mcidHidden[i]));
					messageReceiveService.delMessageReceive(messageReceiveDto);
				}catch(NumberFormatException e){
					continue;
				}
			}
		}
		return this.messgeList(req, resp);
	}
	
	/**
	 * 根据memberCode清除所有的站内信
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MESSGE_DELETE")
	public ModelAndView cleanMessage(HttpServletRequest request, HttpServletResponse response)
	   throws Exception {
		LoginSession loginSession=(LoginSession)request.getSession().getAttribute("userSession");
		String memberCode = loginSession.getMemberCode();
		messageReceiveService.cleanAllMessageReceive(memberCode);
		return this.messgeList(request, response);
	}
	
}
