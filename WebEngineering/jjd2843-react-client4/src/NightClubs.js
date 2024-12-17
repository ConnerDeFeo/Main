import React,{ Component} from "react";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Container, Col, Row, FormGroup, Label, Input } from 'reactstrap';
import ClubItem from "./ClubItem";
import Filter from "./Filter";

class NightClub extends Component{
    constructor(props){
        super(props);
        this.state={
            clubs: [],
            locations:[],
            modalCreate: false,
            nextMaxCap:10,
            nextYellowThreshold:8,
            nextName:"Saturo Gojo", 
            nextLocation:"Somewhere, probably", 
            nextGenre:"Jojo Siwa",

            modalEdit: false,
            editedClub: {maxCap:0, yellowThreshold:0, name:"", location:"", genre:"",id:0},

            filteredClubs: ''
        }
        this.toggleEdit=this.toggleEdit.bind(this);
        this.deleteClub=this.deleteClub.bind(this);
        this.updateCounter=this.updateCounter.bind(this)
        this.updateClubsHidden=this.updateClubsHidden.bind(this);
    }

    toggleCreate=()=>{this.setState({modalCreate:!this.state.modalCreate})}

    toggleEdit=()=>{this.setState({modalEdit:!this.state.modalEdit})}

    checkResponse=(response)=>{
        if(response.status===200){
            return(response.json());
        }
        else{
            console.log("HTTP error:" + response.status + ":" +  response.statusText);
            return (false);
        }
    }

    fetchClubs=()=>{
        fetch('http://localhost:5000/clubs').then((response)=>{
            return this.checkResponse(response);
        }).then((jsonOutput)=>{

            this.setState({clubs:jsonOutput,locations:[...new Set(jsonOutput.map((club)=>{return club.location;}))]});
        }).catch((error) => {console.log(error);this.setState({clubs:false})})        
    }

    componentDidMount(){
        this.fetchClubs();
    }

    render(){
        if(!this.state.clubs){
            return(
            <Container className="d-flex justify-content-center">
                <p>Cannot Connect to Server :/</p>
            </Container>);
        }
        return(
            <div>
                <Container className="d-flex justify-content-center">
                    <Filter updateClubsHidden={this.updateClubsHidden} locations={this.state.locations}/>
                </Container>
                <Container>
                    <Row>
                        {this.state.clubs.filter((club)=>!club.isHidden).map((club)=>(
                                <Col lg={3} md={6} sm={12} key={club.id}>
                                    <ClubItem
                                    key={club.id}
                                    id={club.id}
                                    maxCap={club.maxCap}
                                    yellowThreshold={club.yellowThreshold}
                                    name={club.name}
                                    location={club.location}
                                    genre={club.genre}
                                    counter={club.counter}
                                    incrementCounter={()=>this.updateCounter(club.id,1)}
                                    decrementCounter={()=>this.updateCounter(club.id,-1)}
                                    toggleEdit={()=>{
                                        this.setState({ editedClub: {...club}});
                                        this.toggleEdit();
                                    }}
                                    deleteClub={()=>this.deleteClub(club.id)}
                                    />  
                                </Col> 
                            ))}   
                    </Row>          
                </Container>
                <Container className="d-flex justify-content-center">
                    <Button color="success" onClick={this.toggleCreate}>Add Club</Button>
                </Container>
                <Modal isOpen={this.state.modalCreate} toggle={this.toggleCreate}>
                    <ModalHeader>Add Club</ModalHeader>
                    <ModalBody>
                        <FormGroup row>
                            <Label>Name:</Label>
                            <Input col type="text" value={this.state.nextName} onChange={(e)=>this.setState({nextName:e.target.value})}/>
                        </FormGroup>
                        <FormGroup row>
                            <Label>Location:</Label>
                            <Input type="text" value={this.state.nextLocation} onChange={(e)=>this.setState({nextLocation:e.target.value})}/>
                        </FormGroup>
                        <FormGroup row>
                            <Label>Music Genre:</Label>
                            <Input type="text" value={this.state.nextGenre} onChange={(e)=>this.setState({nextGenre:e.target.value})}/>
                        </FormGroup>
                        <FormGroup row>
                            <Label>Yellow Threshold:</Label>
                            <Input type="number" value={this.state.nextYellowThreshold} onChange={(e)=>this.setState({nextYellowThreshold:e.target.value})}/>
                        </FormGroup>
                        <FormGroup row>
                            <Label>Max Capacity:</Label>
                            <Input type="number" value={this.state.nextMaxCap} onChange={(e)=>this.setState({nextMaxCap:e.target.value})}/>
                        </FormGroup>
                    </ModalBody>
                    <ModalFooter>
                        <Container>
                            <Row>
                                <Col className="d-flex justify-content-center">
                                    <Button color="success" onClick={()=>{this.addClub();this.toggleCreate()}}>Add</Button>
                                </Col>
                                <Col className="d-flex justify-content-center">
                                    <Button color="danger" onClick={this.toggleCreate}>Cancel</Button>
                                </Col>
                            </Row>
                        </Container>
                    </ModalFooter>
                </Modal>
                <Modal isOpen={this.state.modalEdit} toggle={this.toggleEdit}>
                    <ModalHeader>Edit Club</ModalHeader>
                    <ModalBody>
                        <FormGroup row><label>Name:</label><input type="text" value={this.state.editedClub.name} onChange={(e)=>{
                            let newEditedClub={...this.state.editedClub};
                            newEditedClub.name=e.target.value;
                            this.setState({editedClub:newEditedClub});
                        }}/></FormGroup>
                        <FormGroup row><label>Location:</label><input type="text" value={this.state.editedClub.location} onChange={(e)=>{
                            let newEditedClub={...this.state.editedClub};
                            newEditedClub.location=e.target.value;
                            this.setState({editedClub:newEditedClub});
                        }}/></FormGroup>
                        <FormGroup row><label>Music Genre:</label><input type="text" value={this.state.editedClub.genre} onChange={(e)=>{
                            let newEditedClub={...this.state.editedClub};
                            newEditedClub.genre=e.target.value;
                            this.setState({editedClub:newEditedClub});
                        }}/></FormGroup>
                        <FormGroup row><label>Yellow Threshold:</label><input type="number" value={this.state.editedClub.yellowThreshold} onChange={(e)=>{
                            const value=parseInt(e.target.value);
                            if(value>=0 || isNaN(value)){
                                let newEditedClub={...this.state.editedClub};
                                newEditedClub.yellowThreshold=value;
                                this.setState({editedClub:newEditedClub});
                            }
                        }}/></FormGroup>
                        <FormGroup row><label>Max Capacity:</label><input type="number" value={this.state.editedClub.maxCap} onChange={(e)=>{
                            const value=parseInt(e.target.value);
                            if(value>=0 || isNaN(value)){
                                let newEditedClub={...this.state.editedClub};
                                newEditedClub.maxCap=value;
                                newEditedClub.yellowThreshold=Math.round(value*0.8);
                                this.setState({editedClub:newEditedClub});
                            }
                        }}/></FormGroup>
                    </ModalBody>
                    <ModalFooter>
                        <Container>
                            <Row>
                                <Col className="d-flex justify-content-center">
                                    <Button color="success" onClick={()=>{
                                            let fixedEditedClub={...this.state.editedClub};
                                            if(fixedEditedClub.counter>fixedEditedClub.maxCap){
                                                fixedEditedClub.counter=fixedEditedClub.maxCap;
                                            }
                                            fetch('http://localhost:5000/clubs',{
                                                method:'PUT',
                                                body:JSON.stringify({...fixedEditedClub}),
                                                headers:{"Content-type": "application/json; charset=UTF-8"}
                                            }).then((response)=>{return this.checkResponse(response)}).then((jsonOutput)=>{
                                                this.setState({clubs:jsonOutput,locations:[...new Set(jsonOutput.map((club)=>{return club.location;}))]})
                                            }).catch((error)=>{
                                                
                                            })
                                            this.toggleEdit();
                                        }}>Confirm
                                    </Button>
                                </Col>
                                <Col className="d-flex justify-content-center">
                                    <Button color="danger" onClick={this.toggleEdit}>Cancel</Button>
                                </Col>
                            </Row>
                        </Container>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }

    addClub(){
        let newLocations=[...this.state.locations];
        if(!this.state.locations.includes(this.state.nextLocation)){
            newLocations.push(this.state.nextLocation);
        }
        fetch('http://localhost:5000/clubs',{
            method:'POST',
            body:JSON.stringify({
            maxCap:this.state.nextMaxCap, 
            yellowThreshold:this.state.nextYellowThreshold, 
            name:this.state.nextName, 
            location:this.state.nextLocation, 
            genre:this.state.nextGenre
            }),
            headers:{"Content-type": "application/json; charset=UTF-8"}
        }).then((response)=>{
            return this.checkResponse(response);
        }).then((jsonOutput)=>{
            this.setState({clubs:jsonOutput,locations:newLocations});
        })
        
    }

    deleteClub(id){
        fetch('http://localhost:5000/clubs?id='+id,{
            method:'DELETE'
        }).then((response)=>{return this.checkResponse(response)}).then((jsonOutput)=>{
            this.setState({clubs:jsonOutput});
        })
    }

    updateCounter(userId,num){
        fetch('http://localhost:5000/counter',{
            method:'PUT',
            body: JSON.stringify({
                id:userId,
                number:num
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then((response)=>{
            return this.checkResponse(response);
        }).then((jsonOutput)=>{
            this.setState({clubs:jsonOutput});
        })
    }

    updateClubsHidden(location){
        fetch('http://localhost:5000/filter?location='+location,{
            method:'PUT'
        }).then((response)=>{
            return this.checkResponse(response);
        }).then((jsonOutput)=>{
            this.setState({clubs:jsonOutput});
        })
    }
}

export default NightClub;