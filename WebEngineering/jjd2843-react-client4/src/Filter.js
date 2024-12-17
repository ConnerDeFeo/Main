import React,{ Component } from "react"
import { Dropdown, DropdownItem, DropdownMenu, DropdownToggle } from "reactstrap";

class Filter extends Component{
    constructor(props){
        super(props);
        this.state={
            dropDownOpen:false,
            selectedItem: "No Filter"
        }
    }
    toggle=()=>{this.setState({dropDownOpen:!this.state.dropDownOpen})}
    render(){
        return(
            <div>
                <Dropdown isOpen={this.state.dropDownOpen} toggle={this.toggle}>
                    <DropdownToggle caret>{this.state.selectedItem}</DropdownToggle>
                    <DropdownMenu>
                        <DropdownItem header>Locations</DropdownItem>
                        {this.props.locations.map((location)=>(
                            <DropdownItem onClick={()=>{this.props.updateClubsHidden(location);this.setState({selectedItem:location})}}>{location}</DropdownItem>
                        ))}
                        <DropdownItem className="bg-secondary text-light" onClick={()=>{this.props.updateClubsHidden("");this.setState({selectedItem:"No Filter"})}}>No Filter</DropdownItem>
                    </DropdownMenu> 
                </Dropdown>
            </div>
        );
    }
}

export default Filter;