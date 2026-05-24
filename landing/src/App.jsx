import { useState, useEffect } from 'react'
import Hero from './components/Hero'
import StatsBar from './components/StatsBar'
import Features from './components/Features'
import Roles from './components/Roles'
import Download from './components/Download'
import TechStack from './components/TechStack'
import Footer from './components/Footer'
import AuthModal from './components/AuthModal'
import { getToken, logout } from './services/auth'

export default function App() {
  const [showModal, setShowModal] = useState(false)
  const [user, setUser] = useState(null)

  useEffect(() => {
    const token = getToken()
    if (token) {
      const stored = localStorage.getItem('fithero_user')
      if (stored) setUser(stored)
    }
  }, [])

  function handleSuccess(username) {
    localStorage.setItem('fithero_user', username)
    setUser(username)
    setShowModal(false)
  }

  function handleLogout() {
    logout()
    localStorage.removeItem('fithero_user')
    setUser(null)
  }

  return (
    <>
      <Hero
        user={user}
        onLoginClick={() => setShowModal(true)}
        onLogout={handleLogout}
      />
      <StatsBar />
      <Features />
      <Roles />
      <Download />
      <TechStack />
      <Footer />

      {showModal && (
        <AuthModal
          onClose={() => setShowModal(false)}
          onSuccess={handleSuccess}
        />
      )}
    </>
  )
}
