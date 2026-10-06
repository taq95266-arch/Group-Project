import React, { Suspense, useMemo, useRef, useState } from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import { ContactShadows, Environment, Float, Html, PresentationControls } from "@react-three/drei";
import * as THREE from "three";
import "./CarServiceHome.css";

const ORANGE = "#ff7a18";
const BLUE = "#59b7ff";

function Wheel({ position }) {
  return <group position={position} rotation={[0, 0, Math.PI / 2]}>
    <mesh castShadow>
      <cylinderGeometry args={[0.43, 0.43, 0.24, 32]} />
      <meshStandardMaterial color="#11151a" roughness={0.78} />
    </mesh>
    <mesh>
      <cylinderGeometry args={[0.25, 0.25, 0.255, 12]} />
      <meshStandardMaterial color="#9ba5ae" metalness={0.85} roughness={0.25} />
    </mesh>
    <mesh position={[0, 0, 0.14]}>
      <cylinderGeometry args={[0.09, 0.09, 0.02, 12]} />
      <meshStandardMaterial color="#303944" metalness={0.8} />
    </mesh>
  </group>;
}

function Door({ side, open }) {
  const pivot = useRef();
  useFrame((_, dt) => {
    if (!pivot.current) return;
    const target = open ? side * 0.95 : 0;
    pivot.current.rotation.y = THREE.MathUtils.damp(pivot.current.rotation.y, target, 5, dt);
  });
  return <group ref={pivot} position={[side * 0.72, 0.55, 0.12]}>
    <mesh position={[side * 0.42, 0, 0]} castShadow>
      <boxGeometry args={[0.82, 0.52, 0.12]} />
      <meshStandardMaterial color="#626d78" metalness={0.82} roughness={0.23} />
    </mesh>
    <mesh position={[side * 0.42, 0.22, 0.01]}>
      <boxGeometry args={[0.72, 0.24, 0.08]} />
      <meshStandardMaterial color="#18232d" metalness={0.4} roughness={0.15} />
    </mesh>
    <mesh position={[side * 0.72, -0.16, 0.08]}>
      <boxGeometry args={[0.13, 0.035, 0.025]} />
      <meshStandardMaterial color="#d7e0e8" metalness={0.8} />
    </mesh>
  </group>;
}

function CarModel({ open }) {
  const root = useRef();
  useFrame((state, dt) => {
    if (!root.current) return;
    const { pointer } = state;
    root.current.rotation.y = THREE.MathUtils.damp(root.current.rotation.y, pointer.x * 0.22, 3, dt);
    root.current.rotation.z = THREE.MathUtils.damp(root.current.rotation.z, -pointer.y * 0.035, 3, dt);
  });
  return <group ref={root} position={[0, -0.2, 0]} scale={1.15}>
    {/* lower body */}
    <mesh castShadow receiveShadow position={[0, 0.38, 0]}>
      <boxGeometry args={[2.85, 0.48, 1.18]} />
      <meshStandardMaterial color="#6f7d89" metalness={0.88} roughness={0.2} />
    </mesh>
    {/* hood / trunk */}
    <mesh castShadow position={[0, 0.57, 0.65]}>
      <boxGeometry args={[0.95, 0.25, 0.42]} />
      <meshStandardMaterial color="#9ca8b1" metalness={0.85} roughness={0.22} />
    </mesh>
    <mesh castShadow position={[0, 0.57, -0.65]}>
      <boxGeometry args={[0.78, 0.24, 0.36]} />
      <meshStandardMaterial color="#677580" metalness={0.85} roughness={0.22} />
    </mesh>
    {/* cabin */}
    <mesh castShadow position={[0, 0.88, -0.02]} rotation={[0, 0, 0]}>
      <boxGeometry args={[1.62, 0.56, 0.88]} />
      <meshStandardMaterial color="#1b2935" metalness={0.55} roughness={0.16} />
    </mesh>
    <mesh position={[0, 1.17, 0.02]}>
      <boxGeometry args={[1.35, 0.055, 0.75]} />
      <meshStandardMaterial color="#263b4b" metalness={0.5} roughness={0.12} />
    </mesh>
    {/* front lights */}
    {[-1, 1].map(s => <mesh key={s} position={[s * 0.99, 0.48, 0.604]}>
      <boxGeometry args={[0.42, 0.09, 0.025]} />
      <meshStandardMaterial color="#d9f3ff" emissive="#77cfff" emissiveIntensity={2.2} />
    </mesh>)}
    {/* grille */}
    <mesh position={[0, 0.34, 0.61]}>
      <boxGeometry args={[0.72, 0.23, 0.04]} />
      <meshStandardMaterial color="#10161c" metalness={0.65} roughness={0.3} />
    </mesh>
    {[-1, 1].map(s => <React.Fragment key={s}>
      <Wheel position={[s * 1.03, 0.25, 0.42]} />
      <Wheel position={[s * 1.03, 0.25, -0.43]} />
      <Door side={s} open={open} />
    </React.Fragment>)}
    {/* side sill */}
    <mesh position={[0, 0.17, 0]}>
      <boxGeometry args={[2.15, 0.09, 1.16]} />
      <meshStandardMaterial color="#303c46" metalness={0.75} roughness={0.28} />
    </mesh>
  </group>;
}

function FloatingPart({ type, position, index, active, setActive }) {
  const ref = useRef();
  useFrame((state, dt) => {
    if (!ref.current) return;
    const t = state.clock.elapsedTime;
    ref.current.position.y = position[1] + Math.sin(t * 1.25 + index) * 0.11;
    const target = active ? 1.28 : 1;
    ref.current.scale.setScalar(THREE.MathUtils.damp(ref.current.scale.x, target, 5, dt));
    ref.current.rotation.y += dt * (active ? 0.3 : 0.12);
  });
  return <group ref={ref} position={position} onPointerOver={e => {e.stopPropagation();setActive(index);document.body.style.cursor="pointer";}} onPointerOut={() => {setActive(-1);document.body.style.cursor="default";}}>
    {type==="Tire" ? <group rotation={[0,0,Math.PI/2]}>
      <mesh><torusGeometry args={[0.35,0.13,12,24]}/><meshStandardMaterial color="#15191d" roughness={0.85}/></mesh>
      <mesh><cylinderGeometry args={[0.21,0.21,0.12,12]}/><meshStandardMaterial color="#9ba9b3" metalness={0.9}/></mesh>
    </group> : type==="Oil" ? <group>
      <mesh position={[0,-0.02,0]}><cylinderGeometry args={[0.19,0.22,0.48,6]}/><meshStandardMaterial color="#202a34" metalness={0.5} roughness={0.35}/></mesh>
      <mesh position={[0,0.25,0]}><cylinderGeometry args={[0.08,0.08,0.08,8]}/><meshStandardMaterial color={ORANGE}/></mesh>
      <mesh position={[0,0.02,0.115]}><boxGeometry args={[0.24,0.16,0.02]}/><meshStandardMaterial color="#f0f2f4"/></mesh>
    </group> : type==="Tools" ? <group rotation={[0,0,-0.5]}>
      {[0,1].map(i=><mesh key={i} position={[i*0.16-0.08,0,0]} rotation={[0,0,i*0.55]}>
        <boxGeometry args={[0.075,0.68,0.045]}/><meshStandardMaterial color="#b8c2ca" metalness={0.95} roughness={0.18}/>
      </mesh>)}
      <mesh position={[-0.1,0.3,0]}><torusGeometry args={[0.09,0.035,8,14]}/><meshStandardMaterial color="#b8c2ca" metalness={0.9}/></mesh>
    </group> : type==="Battery" ? <group>
      <mesh><boxGeometry args={[0.5,0.34,0.3]}/><meshStandardMaterial color="#25394b" metalness={0.55} roughness={0.3}/></mesh>
      {[-0.14,0.14].map(x=><mesh key={x} position={[x,0.2,0]}><cylinderGeometry args={[0.055,0.055,0.06,8]}/><meshStandardMaterial color={x<0?"#e85c4a":"#d8dfe4"} metalness={0.7}/></mesh>)}
    </group> : <group>
      <mesh><cylinderGeometry args={[0.25,0.25,0.13,16]}/><meshStandardMaterial color="#9daab4" metalness={0.9}/></mesh>
      <mesh position={[0,0.08,0]}><torusGeometry args={[0.16,0.045,8,16]}/><meshStandardMaterial color="#dc553c" metalness={0.5}/></mesh>
    </group>}
    {active===index && <Html center distanceFactor={7}><div className="part-label">{type}</div></Html>}
  </group>;
}

function Scene() {
  const [open,setOpen]=useState(false), [active,setActive]=useState(-1);
  const parts = [
    {type:"Oil",position:[-2.1,1.3,0.2]},
    {type:"Tire",position:[-0.45,1.75,-0.2]},
    {type:"Tools",position:[1.8,1.35,0.1]},
    {type:"Battery",position:[2.2,0.25,-0.2]},
    {type:"Parts",position:[-1.9,0.15,-0.2]},
    {type:"Brake",position:[1.65,-0.55,0.1]}
  ];
  return <group onPointerMissed={()=>setOpen(false)}>
    <ambientLight intensity={0.75}/>
    <spotLight position={[3,6,5]} intensity={90} angle={0.55} penumbra={0.7} castShadow/>
    <pointLight position={[-4,2,2]} intensity={25} color="#5bbcff"/>
    <pointLight position={[4,1,-3]} intensity={20} color="#ff7a18"/>
    <PresentationControls global polar={[-0.12,0.15]} azimuth={[-0.3,0.3]} config={{mass:2,tension:160}} snap={{mass:4,tension:180}}>
      <group onPointerOver={()=>setOpen(true)} onPointerOut={()=>setOpen(false)}>
        <CarModel open={open}/>
      </group>
      {parts.map((p,i)=><FloatingPart key={p.type} {...p} index={i} active={active} setActive={setActive}/>)}
    </PresentationControls>
    <mesh rotation={[-Math.PI/2,0,0]} position={[0,-0.62,0]} receiveShadow>
      <circleGeometry args={[4.2,64]}/><meshStandardMaterial color="#17212a" metalness={0.55} roughness={0.34}/>
    </mesh>
    <ContactShadows position={[0,-0.6,0]} opacity={0.6} scale={8} blur={2.8}/>
    <Environment preset="city"/>
  </group>;
}

const services=[
 ["Engine Oil Change","Keep your engine running smooth and efficient.","oil"],
 ["Tire Replacement","Better grip, safer drives.","tire"],
 ["Car Repair","Expert repairs for all makes & models.","repair"],
 ["Battery Replacement","Reliable power, anytime.","battery"],
 ["Emergency Assistance","Help when you need it most.","road"]
];
const steps=[
 ["Book Online","Choose your service and select a convenient time.","calendar-days"],
 ["Visit the Garage","Take your car to one of our trusted garages.","warehouse"],
 ["Service & Repair","Our experts handle the rest.","wrench"],
 ["Get Back on the Road","Your car is ready, with complete peace of mind.","car-front"]
];

export default function CarServiceHome(){
 return <main className="car-home">
  <nav className="top-nav">
   <a className="brand" href="#"><span className="brand-mark">↗</span> CarService</a>
   <div className="nav-links"><a href="#">Home</a><a href="#services">Services</a><a href="#how">How It Works</a><a href="#why">About</a><a href="#contact">Contact</a></div>
   <div className="nav-actions"><a className="login" href="#">Login</a><a className="btn btn-orange small" href="#contact">Book a Service</a></div>
  </nav>
  <section className="hero">
   <div className="hero-copy"><span className="eyebrow">CAR CARE, REIMAGINED</span><h1>Your Car,<br/><em>Our Priority</em></h1><p>Professional car services, trusted garages, and expert technicians — all in one place.</p><div className="hero-buttons"><a className="btn btn-orange" href="#services">Explore Services <span>→</span></a><a className="btn btn-outline" href="#contact">▦ &nbsp; Book a Service</a></div><div className="scroll-hint">↓ &nbsp; Scroll to explore</div></div>
   <div className="scene-wrap"><Suspense fallback={<div className="scene-loading">Preparing your experience…</div>}><Canvas shadows camera={{position:[4,2.5,6.8],fov:38}} dpr={[1,1.6]}><Scene/></Canvas></Suspense><div className="scene-caption">INTERACTIVE 3D EXPERIENCE <span>• DRAG TO EXPLORE</span></div></div>
   <div className="hero-index">01 <i/> 02&nbsp; 03</div>
  </section>
  <section className="services section" id="services"><div className="section-intro"><span className="eyebrow">OUR SERVICES</span><h2>Complete Car Care<br/><em>Under One Roof</em></h2><p>From routine maintenance to complex repairs, we've got you covered.</p><a className="btn btn-outline" href="#contact">View All Services&nbsp; →</a></div><div className="service-grid">{services.map((s,i)=><article className="service-card" key={s[0]}><div className={`service-art art-${s[2]}`}><span>{["◉","◌","⚒","ϟ","⌁"][i]}</span></div><h3>{s[0]}</h3><p>{s[1]}</p><a href="#contact" aria-label={`Explore ${s[0]}`}>↗</a></article>)}</div></section>
  <section className="how section" id="how"><div className="section-intro"><span className="eyebrow">HOW IT WORKS</span><h2>Get Your Car Service<br/><em>in 4 Simple Steps</em></h2><p>Book, track, and get your car serviced — it's that easy!</p><a className="btn btn-outline" href="#contact">Learn More&nbsp; →</a></div><div className="steps">{steps.map((s,i)=><div className="step" key={s[0]}><div className="step-icon">{["▦","⌂","🔧","▱"][i]}</div><h3>{i+1}. {s[0]}</h3><p>{s[1]}</p></div>)}</div></section>
  <section className="why section" id="why"><div className="mechanic-photo"><div className="photo-overlay">CAR SERVICE <b>EXPERTS</b></div></div><div className="why-copy"><span className="eyebrow">WHY CHOOSE US</span><h2>Your Trusted Partner<br/><em>in Car Care</em></h2><p>We're committed to providing the best service, quality, and convenience for you and your car.</p><div className="benefits">{[["Trusted Garages","Work with certified and verified garages."],["Expert Technicians","Skilled and experienced professionals."],["Fast & Easy Booking","Save time with our simple booking process."],["Secure & Reliable","Your data and car are always protected."]].map(([t,d],i)=><div className="benefit" key={t}><span>{["♢","♙","◷","✓"][i]}</span><div><h3>{t}</h3><p>{d}</p></div></div>)}</div></div></section>
  <section className="cta" id="contact"><div><span className="eyebrow">READY TO GET STARTED?</span><h2>Your Car <em>Deserves the Best</em></h2><p>Book your service today and drive with confidence.</p></div><a className="btn btn-orange" href="mailto:hello@carservice.example">Book a Service&nbsp; →</a></section>
  <footer><a className="brand" href="#"><span className="brand-mark">↗</span> CarService</a><small>Drive Better. Live Safer.</small><div><a href="#">Home</a><a href="#services">Services</a><a href="#how">How It Works</a><a href="#why">About</a><a href="#contact">Contact</a></div><small>© 2026 CarService</small></footer>
 </main>
}
