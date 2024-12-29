import { Col, Container, Row } from "reactstrap";

function ProjectPage({title,type,languages,date,video,description}){
    return(
        <>
            <Container className="fs-4">
                <h1 className="col-12 mt-4 fs-1">{title}</h1>
                <Row className="mt-2">
                    <Col>
                        <em>{type}</em> - {languages}
                    </Col>
                    <Col className="text-end">
                        {date}
                    </Col>
                </Row>
                <Row className="my-4">
                    <video src={video} alt="NoImg" className="mx-auto"/>
                </Row>
                <Row>
                    {description}
                </Row>
            </Container>
        </>
    );
}

export default ProjectPage;