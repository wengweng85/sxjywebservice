create or replace package pkg_jk_server AS

  --插入主日志
  procedure ADD_LOG(
      in_jk_type in varchar2,
      in_jk_sender in varchar2,
      in_jk_receiver in varchar2,
      in_jk_key in varchar2,
      in_jk_send_xml in varchar2,
      in_jk_id out varchar2
  );


  --更新主日志
  procedure UPDATE_LOG(
        log_id in varchar2,--流水号
        in_jk_result in varchar2,--成功标志 (0 失败 1 成功)
        in_jk_return_xml IN VARCHAR2 ,--返回xml串 head
        in_jk_return_code IN VARCHAR2,--业务错误码
        in_jk_return_msg in VARCHAR2 --业务错误说明
  );

    --获得jkkey
  procedure GET_KEY(
      in_ym in VARCHAR2,
      out_key OUT VARCHAR2
  );

end;
/
create or replace package body pkg_jk_server AS
    in_jk_id varchar2(10);

    /**-------------插入主日志---------------------------*/
    procedure ADD_LOG(
      in_jk_type in varchar2,
      in_jk_sender in varchar2,
      in_jk_receiver in varchar2,
      in_jk_key in varchar2,
      in_jk_send_xml in varchar2,
      in_jk_id out varchar2
    )
    as
    begin
       --插入日志
        select seq_log.nextval into in_jk_id from dual;
        insert into jk_log(
            jk_id, --接口日志表主键
            jk_key, --业务流水号
            jk_send_xml,--接口请求数据xml head
            jk_type, --接口类型
            jk_sender,--接口调用方
            jk_receiver,--接口调用方
            jk_call_begin--接口调用开始时间
        )
        values(
            in_jk_id, --接口日志表主键
            in_jk_key, --业务流水号
            in_jk_send_xml,--接口请求数据xml head
            in_jk_type, --接口类型
            in_jk_sender,--接口调用方
            in_jk_receiver,--接口调用方
            SYSDATE--接口调用开始时间
           );
    end;

    --更新主日志
    procedure UPDATE_LOG(
        log_id in varchar2,--流水号
        in_jk_result in varchar2,--成功标志 (0 失败 1 成功)
        in_jk_return_xml IN VARCHAR2 ,--返回xml串 head
        in_jk_return_code IN VARCHAR2,--业务错误码
        in_jk_return_msg in VARCHAR2 --业务错误说明
    )
    as
    begin
       --更新主日志
       update jk_log t set
       t.jk_call_end=sysdate,
       jk_result=in_jk_result ,
       jk_return_xml=in_jk_return_xml,
       jk_return_code=in_jk_return_code,
       jk_return_msg=in_jk_return_msg
       where jk_id=log_id;
    end;



    --获得jkkey
    procedure GET_KEY(
      in_ym in VARCHAR2,
      out_key OUT VARCHAR2
    ) AS
    ls_count INTEGER;
    BEGIN
       select count(*) INTO ls_count FROM jk_key t WHERE t.ym=in_ym;
       IF ls_count=0 THEN
         INSERT INTO jk_key(ym,SQNUM) VALUES (in_ym,0);
       END IF;
         UPDATE jk_key t SET SQNUM=SQNUM+1 WHERE  t.ym=in_ym;
         SELECT lpad(SQNUM,8,0) INTO out_key FROM jk_key t WHERE t.ym=in_ym;
    END;




end;
/
