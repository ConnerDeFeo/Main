import {Col, Container, Form, Input, Row } from 'reactstrap';
import Track from './images/Track.jpg';
import { useState } from 'react';
import emailjs from 'emailjs-com';

function Contact(){
    const [name,setName]=useState('');
    const [eMail,setEMail]=useState('');
    const [message,setMessage]=useState('');

    const sendEmail=(e)=>{
        //Once published, add reCaptcha.
        e.preventDefault();
        emailjs.sendForm('service_ho7d22t', 'template_e1yhk0c', e.target, 'dHmN6Poxt2OKm6_FU')
          .then((result) => {
              window.location.reload()
          }, (error) => {
              console.log(error.text);
          });
      }

    return(
        <div className='bg-dark text-light'>
            <Container className='mw-100 pt-5'>
                <Row>
                    <Col className='col-12 col-md-8 my-auto'>
                        <Container className='vh-80'>
                            <Form onSubmit={sendEmail}>
                                <Row>Contact</Row>
                                <Row ><Input className='formNameEmail border border-black' placeholder={"Name"} type='text' name='from_name' value={name} onChange={(e)=>setName(e.target.value)}/></Row>
                                <Row><Input className='formNameEmail border border-black' placeholder={"E-Mail"} type='text' value={eMail} name='from_email' onChange={(e)=>setEMail(e.target.value)}/></Row>
                                <Row><textarea className='formMessage border border-black' placeholder={"Message"} type='text' name='message' value={message} onChange={(e)=>setMessage(e.target.value)} style={{resize:'none'}}/></Row>
                                <Row><Input type='submit' value='Send Email' className=' bg-secondary border border-black submitButton'/></Row>
                            </Form>
                        </Container>
                    </Col>
                    <Col className='col-12 col-md-4 d-grid justify-content-center text-center mt-5'>
                        <img src={Track} alt='Track' className='contactImageCustom'/>
                        <p>Photo: Autumn Bernava</p>
                    </Col>
                </Row>
            </Container>
            <Container className='mw-100'>
                <div className='decorativeLine my-5'></div>
                <Row>
                    <Col className='text-center'>
                        <p>Phone</p>
                        <p>315-879-7067</p>
                    </Col>
                    <Col className='text-center'>
                        <p>Email</p>
                        <p>jjd2843@g.rit.edu</p>
                    </Col>
                </Row>
                <div className='decorativeLine mt-5'></div>
            </Container>
        </div>
    );
}

export default Contact;