# -*- coding: utf-8 -*-
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import NoAlertPresentException
import unittest, time, re

class LobbyTest(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.PhantomJS()
        self.driver.implicitly_wait(30)
        self.base_url = "http://test"
        self.verificationErrors = []
        self.accept_next_alert = True
    
    def test_lobby(self):
        driver = self.driver
        driver.get(self.base_url + "/games/lobby.htm")
        try: self.assertEqual("Anonymous", driver.find_element_by_id("usernameInput").get_attribute("value"))
        except AssertionError as e: self.verificationErrors.append(str(e))
        try: self.assertTrue(self.is_element_present(By.CSS_SELECTOR, "span.dialogButtonText"))
        except AssertionError as e: self.verificationErrors.append(str(e))
        self.assertEqual("Welcome to the chess lobby!", driver.find_element_by_css_selector("h2").text)
        self.assertEqual("Join the lobby to:", driver.find_element_by_css_selector("center").text)
        self.assertEqual("Chess Lobby", driver.find_element_by_link_text("Chess Lobby").text)
        self.assertEqual("Play Chess (same browser)", driver.find_element_by_link_text("Play Chess (same browser)").text)
    
    def is_element_present(self, how, what):
        try: self.driver.find_element(by=how, value=what)
        except NoSuchElementException as e: return False
        return True
    
    def is_alert_present(self):
        try: self.driver.switch_to_alert()
        except NoAlertPresentException as e: return False
        return True
    
    def close_alert_and_get_its_text(self):
        try:
            alert = self.driver.switch_to_alert()
            alert_text = alert.text
            if self.accept_next_alert:
                alert.accept()
            else:
                alert.dismiss()
            return alert_text
        finally: self.accept_next_alert = True
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()

